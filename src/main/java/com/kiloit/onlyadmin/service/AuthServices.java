package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.constant.MessageConstant.REGISTERPROPERTY;
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
import com.kiloit.onlyadmin.util.MailSeverTemplate;
import com.kiloit.onlyadmin.util.RandomUntil;
import com.kiloit.onlyadmin.util.TokenUtils;
import com.kiloit.onlyadmin.util.Verification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalTime;
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
    private final UserVerificationRepository userVerificationRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final Verification verification;
    private final MailSeverTemplate mailSeverTemplate;

    @Transactional
    public StructureRS register(RegisterRequest registerRequest) {
        if (userRepository.existsByPhone(registerRequest.phone()))
            throw new BadRequestException(MessageConstant.REGISTERPROPERTY.PHONE_IS_NOT_VALID);
        if (userRepository.existsByEmail(registerRequest.email()))
            throw new BadRequestException(MessageConstant.REGISTERPROPERTY.EMAIL_IS_EXISTING);
        if (userRepository.existsByPhone(registerRequest.username()))
            throw new BadRequestException(MessageConstant.REGISTERPROPERTY.USERNAME_IS_NOT_VALID);
        if (!registerRequest.confirmPassword().equals(registerRequest.password()))
            throw new BadRequestException(MessageConstant.CREDENTIAL.PASSWORD_NOT_MATCH);

        UserEntity user = userMapper.fromRegisterRequest(registerRequest);
        Optional<RoleEntity> role = roleRepository.findById(2L);
        if (role.isEmpty())
            throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        user.setIsVerification(false);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(role.get());
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return response(RegisterResponse.builder().message(REGISTERPROPERTY.REGISTER_HAS_BEEN_SUCCESSFULLY).email(registerRequest.email()).build());
    }

    public StructureRS sendVerification(String email){
        String codeRandom = RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(email, false)
                .orElseThrow(() -> new BadRequestException(MessageConstant.REGISTERPROPERTY.EMAIL_HAS_NOT_BEEN_FOUND));
        verification.verification(user,codeRandom);
        return mailSeverTemplate.prepareTemplateMail(user.getEmail(), user, codeRandom, "User Verification");
    }

    @Transactional
    public StructureRS verify(VerificationRequest verificationRequest) {
        UserEntity user = userRepository
                         .findByEmailAndIsVerificationAndDeletedAtNull(verificationRequest.email(), false)
                         .orElseThrow(() -> new BadRequestException(MessageConstant.REGISTERPROPERTY.EMAIL_HAS_NOT_BEEN_FOUND));
        Optional<UserVerification> verification = userVerificationRepository
                                        .findByUserAndVerifiedCode(user, verificationRequest.verifiedCode());
        if(verification.isPresent()){
            if (LocalTime.now().isAfter(verification.get().getExpiryTime()))
                throw new BadRequestException(MessageConstant.CREDENTIAL.VERIFY_CODE_HAS_BEEN_EXPRIED);
            user.setIsVerification(true);
            userRepository.save(user);
            userVerificationRepository.delete(verification.get());
        }
        else throw new BadRequestException(MessageConstant.CREDENTIAL.VERIFY_CODE_HAS_BEEN_EXPRIED);
        return response(MessageConstant.CREDENTIAL.VERIFY_HAS_BEEN_SUCCESSFULLY);
    }

    @Transactional
    public StructureRS resendVerification(SendVerificationRequest sendVerificationRequest) {
        String codeRandom = RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(sendVerificationRequest.email(), false)
                .orElseThrow(() -> new BadRequestException(MessageConstant.REGISTERPROPERTY.EMAIL_HAS_NOT_BEEN_FOUND));
        verification.verification(user,codeRandom);
        return mailSeverTemplate.prepareTemplateMail(user.getEmail(), user, codeRandom, "User Verification");
    }

    public StructureRS login(LoginRequest loginRequest) {
        if (userRepository.existsByEmailAndIsVerificationAndDeletedAtNull(loginRequest.email(), true)) {
            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                if (!authentication.isAuthenticated()) throw new BadRequestException(MessageConstant.CREDENTIAL.INVALID_EMAIL_OR_PASSWORD);
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                Map<String, Object> respond = new HashMap<>();
                respond.put("user", userPrincipal);
                respond.put("token", tokenUtils.generateToken(userPrincipal));
                return response(respond);
            } catch (AuthenticationException e) {
                throw new BadRequestException(MessageConstant.CREDENTIAL.INVALID_EMAIL_OR_PASSWORD);
            }
        }
        throw new BadRequestException(MessageConstant.CREDENTIAL.INVALID_EMAIL_OR_PASSWORD);
    }

    @Transactional
    public StructureRS resetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(resetPasswordRequest.email(), true)
                .orElseThrow(() -> new BadRequestException(REGISTERPROPERTY.EMAIL_HAS_NOT_BEEN_FOUND));
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
        return mailSeverTemplate.prepareTemplateMail(resetPasswordRequest.email(), user, verifyCode, "Password reset request");
    }

    @Transactional
    public StructureRS handlePasswordReset(VerifyCode verifyCode) {
        Optional<PasswordResetEntity> passwordReset = passwordResetRepository.findByCode(verifyCode.code());
        if (passwordReset.isEmpty()) throw new BadRequestException(MessageConstant.CREDENTIAL.VERIFY_CODE_HAS_BEEN_EXPRIED);
        UserEntity user = passwordReset.get().getUser();
        user.setPassword(passwordEncoder.encode(verifyCode.password()));
        userRepository.save(user);
        passwordResetRepository.delete(passwordReset.get());
        return response(MessageConstant.RESET_PASSWORD_SUCCESSFULLY);
    }

}
