package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.PasswordResetEntity;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.entity.UserVerification;
import com.kiloit.onlyadmin.database.repository.PasswordResetRepository;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.database.repository.UserVerificationRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.model.user.mapper.UserMapper;
import com.kiloit.onlyadmin.model.user.request.auth.*;
import com.kiloit.onlyadmin.model.user.respone.auth.RegisterResponse;
import com.kiloit.onlyadmin.security.UserPrincipal;
import com.kiloit.onlyadmin.util.RandomUntil;
import com.kiloit.onlyadmin.util.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServices extends BaseService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserVerificationRepository userVerificationRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Transactional
    public StructureRS register(RegisterRequest registerRequest) {
        if (userRepository.existsByPhone(registerRequest.phone()))
            throw new BadRequestException("Phone Number has been already exist");
        if (userRepository.existsByEmail(registerRequest.email()))
            throw new BadRequestException("Email has been already exist");
        if (userRepository.existsByPhone(registerRequest.username()))
            throw new BadRequestException("Username has been already exist");
        if (!registerRequest.confirmPassword().equals(registerRequest.password()))
            throw new BadRequestException("Password has not been match");

        UserEntity user = userMapper.fromRegisterRequest(registerRequest);
        Optional<RoleEntity> role = roleRepository.findById(Long.parseLong("2"));
        if (role.isEmpty()) throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        user.setIsVerification(false);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(role.get());
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return response(RegisterResponse.builder().message("You register has been successfully").email(registerRequest.email()).build());
    }

    public StructureRS sendVerification(String email) throws MessagingException {
        String codeRandom = RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(email, false).orElseThrow(() -> new BadRequestException("Email has not been found"));
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(codeRandom);
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(5));
        userVerificationRepository.save(userVerification);
        return prepareTemplateMail(email, user, codeRandom, "User Verification");
    }

    public StructureRS prepareTemplateMail(String email, UserEntity user, String codeRandom, String subject) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        Context context = new Context();
        try {
            templateModel.put("userName", user.getUsername());
            templateModel.put("userEmail", user.getEmail());
            templateModel.put("date", new Date());
            templateModel.put("code", codeRandom);
            context.setVariables(templateModel);

            String htmlContent = templateEngine.process("email-template", context);
            prepareMailSend(email, htmlContent, subject);
            return response(("Email sent!"));
        } catch (MessagingException e) {
            return response("Error sending email!");
        }
    }

    public void prepareMailSend(String toMail, String htmlContent, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(message);
    }

    @Transactional
    public StructureRS verify(VerificationRequest verificationRequest) {
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(verificationRequest.email(), false).orElseThrow(() -> new BadRequestException("Email has not been found"));
        UserVerification verification = userVerificationRepository.findByUserAndVerifiedCode(user, verificationRequest.verifiedCode()).orElseThrow(() -> new BadRequestException("Verified code has expired"));
        if (LocalTime.now().isAfter(verification.getExpiryTime()))
            throw new BadRequestException("Verified code has expired");
        user.setIsVerification(true);
        userRepository.save(user);
        userVerificationRepository.delete(verification);
        return response();
    }

    public StructureRS resendVerification(SendVerificationRequest sendVerificationRequest) {
        String codeRandom = RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(sendVerificationRequest.email(), false).orElseThrow(() -> new BadRequestException("Email has not been found"));
        UserVerification verification = userVerificationRepository.findByUser(user).orElseThrow(() -> new BadRequestException("Verified code has been expired"));
        verification.setVerifiedCode(codeRandom);
        verification.setExpiryTime(LocalTime.now().plusSeconds(60));
        userVerificationRepository.save(verification);
        return prepareTemplateMail(user.getEmail(), user, codeRandom, "User Verification");
    }

    public StructureRS login(LoginRequest loginRequest) {
        if (userRepository.existsByEmailAndIsVerificationAndDeletedAtNull(loginRequest.email(), true)) {

            try {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);

                if (!authentication.isAuthenticated()) throw new BadRequestException("Invalid email or password");

                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

                Map<String, Object> respond = new HashMap<>();

                respond.put("user", userPrincipal);
                respond.put("token", tokenUtils.generateToken(userPrincipal));

                return response(respond);

            } catch (AuthenticationException e) {
                throw new BadRequestException("Invalid email or password");
            }

        }

        throw new BadRequestException("Invalid email or password");
    }


    public StructureRS resetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(resetPasswordRequest.email(), true).orElseThrow(() -> new BadRequestException("Email has not been found"));
        PasswordResetEntity passwordResetEntity = passwordResetRepository.findByUser(user);
        String verifyCode = RandomUntil.random6Digits();

        if (passwordResetEntity == null) {
            passwordResetEntity = new PasswordResetEntity();
            passwordResetEntity.setCode(verifyCode);
            passwordResetEntity.setUser(user);
        } else {
            passwordResetEntity.setCode(verifyCode);
        }
        passwordResetEntity.setExpiryTime(LocalTime.now().plusMinutes(5));
        passwordResetRepository.save(passwordResetEntity);
        return prepareTemplateMail(resetPasswordRequest.email(), user, verifyCode, "Password reset request");
    }

    @Transactional
    public StructureRS handlePasswordReset(VerifyCode verifyCode) {
        Optional<PasswordResetEntity> passwordReset = passwordResetRepository.findByCode(verifyCode.code());
        if (passwordReset.isEmpty()) throw new BadRequestException("Invalid code");
        UserEntity user = passwordReset.get().getUser();
        user.setPassword(passwordEncoder.encode(verifyCode.password()));
        userRepository.save(user);
        passwordResetRepository.delete(passwordReset.get());
        return response("Password reset successful!");
    }

}
