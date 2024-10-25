package com.kiloit.onlyadmin.service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.PasswordResetTokenEntity;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.entity.UserVerification;
import com.kiloit.onlyadmin.database.repository.PasswordResetTokenRepository;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.database.repository.UserVerificationRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.model.user.mapper.UserMapper;
import com.kiloit.onlyadmin.model.user.request.auth.LoginRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RefreshTokenRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.user.request.auth.ResetPasswordRequest;
import com.kiloit.onlyadmin.model.user.request.auth.SendVerificationRequest;
import com.kiloit.onlyadmin.model.user.request.auth.VerificationRequest;
import com.kiloit.onlyadmin.model.user.respone.auth.AuthResponse;
import com.kiloit.onlyadmin.model.user.respone.auth.RegisterResponse;
import com.kiloit.onlyadmin.util.RandomUntil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServices extends BaseService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordResetTokenRepository  passwordResetTokenRepository;

    @Value("${spring.mail.username}")
    private String adminEmail;

    public StructureRS register(RegisterRequest registerRequest) {
        if(userRepository.existsByPhone(registerRequest.phone())) throw new BadRequestException("Phone Number has been already exist");
        if(userRepository.existsByEmail(registerRequest.email())) throw new BadRequestException("Email has been already exist");
        if(!registerRequest.confirmPassword().equals(registerRequest.password())) throw new BadRequestException("Password has not been match");
        
        UserEntity user = userMapper.fromRegisterRequest(registerRequest);
        Optional<RoleEntity> role = roleRepository.findById(Long.parseLong("3"));
        if(role.isEmpty()) throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRole(role.get());
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return response(RegisterResponse.builder().message("You register has been successfully").email(registerRequest.email()).build());
    }

    public StructureRS sendVerification(String email) throws MessagingException{
        String codeRandom= RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new BadRequestException("Email has not been found"));
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(codeRandom);
        userVerification.setExpiryTime(LocalTime.now().plusSeconds(60));
        userVerificationRepository.save(userVerification);
        return prepareTemplateMail(email, user, codeRandom);
    }
    
    public StructureRS prepareTemplateMail(String email,UserEntity user,String codeRandom){
        Map<String,Object> templateModel= new HashMap<String, Object>();
        Context context = new Context();
        try {
            templateModel.put("userName", user.getUsername());
            templateModel.put("userEmail", user.getEmail());
            templateModel.put("registrationDate", new Date());
            templateModel.put("code",codeRandom);
            context.setVariables(templateModel);

            String htmlContent = templateEngine.process("email-template", context);
            prepareMailSend(email, htmlContent,"User Verification","html");
            return response(("Email sent!"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return response("Error sending email!");
        }
    }

    public void prepareMailSend(String toMail,String htmlContent,String subject,String type) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);

        if(type=="html") mimeMessageHelper.setText(htmlContent,true);
        else mimeMessageHelper.setText(htmlContent);
        javaMailSender.send(message);
    }

    public StructureRS verify(VerificationRequest verificationRequest) {
        UserEntity user = userRepository.findByEmail(verificationRequest.email()).orElseThrow(()-> new BadRequestException("Email has not been found"));
        UserVerification verification = userVerificationRepository.findByUserAndVerifiedCode(user,verificationRequest.verifiedCode()).orElseThrow(()-> new BadRequestException("Verified code has expired"));
        if(LocalTime.now().isAfter(verification.getExpiryTime())) throw new BadRequestException("Verified code has expired");
        user.setIsVerification(true);
        userRepository.save(user);
        userVerificationRepository.delete(verification);
        return response();
    }

    public StructureRS resendVerification(SendVerificationRequest sendVerificationRequest) {
        String codeRandom= RandomUntil.random6Digits();
        UserEntity user = userRepository.findByEmail(sendVerificationRequest.email()).orElseThrow(()-> new BadRequestException("Email has not been found"));
        UserVerification verification = userVerificationRepository.findByUser(user).orElseThrow(()-> new BadRequestException("Verified code has been expired"));
        verification.setVerifiedCode(codeRandom);
        verification.setExpiryTime(LocalTime.now().plusSeconds(60));
        userVerificationRepository.save(verification);
        return prepareTemplateMail(user.getEmail(), user, codeRandom);
    }

    public StructureRS login(LoginRequest loginRequest) {
        if(userRepository.existsByEmailAndIsVerificationAndDeletedAt(loginRequest.phoneNumber(),true,null)){
            try{
                Authentication authentication= new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
                authentication = daoAuthenticationProvider.authenticate(authentication);
                if(!authentication.isAuthenticated()) throw new BadRequestException("Invalid email or password");
                String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
                return response(AuthResponse.builder().accessToken(createToken(accessTokenJwtEncoder, createClaimsSet(authentication, scope, "dao"))).refeshToken(createToken(refreshTokenJwtEncoder, createClaimsSet(authentication, scope, "dao-jwt"))).tokenType("Bearer").build());
            }catch(AuthenticationException e){
                throw new BadRequestException("Invalid email or password");
            }
        } else throw new BadRequestException("Username has not been found");
    }

    public StructureRS refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String accessToken;
        String refreshToken= refreshTokenRequest.refreshToken();
        Authentication authentication = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        authentication=jwtAuthenticationProvider.authenticate(authentication);
        if(Duration.between(Instant.now(), ((Jwt)authentication.getPrincipal()).getExpiresAt()).toDays()<=1) refreshToken=createToken(accessTokenJwtEncoder, createClaimsSet(authentication, null,"jwt"));
        accessToken = createToken(accessTokenJwtEncoder, createClaimsSet(authentication,null,"jwt"));
        return response(AuthResponse.builder().tokenType("Bearer").accessToken(accessToken).refeshToken(refreshToken).build());
    }

    public String createToken(JwtEncoder tokentJwtEncoder,JwtClaimsSet jwtClaimsSet) {return tokentJwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();}

    public JwtClaimsSet createClaimsSet(Authentication authentication,String scope,String type){
        Instant expired=Instant.now().plus(30,ChronoUnit.MINUTES);
        String id =authentication.getName();
        String subject_="Access APIs";
        String issuer=id;
        Instant issueAt=Instant.now();
        Boolean claimValue1=true;
        String claimValue2=scope;

        if(type=="jwt"){
            Jwt jwt= (Jwt)authentication.getPrincipal();
            id=jwt.getId();
            issuer=id;
            expired=Instant.now().plus(30,ChronoUnit.DAYS);
            claimValue2=jwt.getClaimAsString("scope");
        }
        else if(type=="dao-jwt") expired= Instant.now().plus(30,ChronoUnit.DAYS);
        return JwtClaimsSet.builder().id(id).subject(subject_).issuer(issuer).issuedAt(issueAt).expiresAt(expired).claim("isAdmin", claimValue1).claim("scope", claimValue2).build();
    }

    public StructureRS resetPassword(ResetPasswordRequest resetPasswordRequest){
        UserEntity user = userRepository.findByEmail(resetPasswordRequest.email()).orElseThrow(()-> new BadRequestException("Email has not been found"));
        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryTime(LocalTime.now().plusHours(24));
        passwordResetTokenRepository.save(passwordResetToken);
        try {
            String resetUrl = "http://localhost:8080/api/v1/auth/reset-password?token=" + token;
            prepareMailSend(user.getEmail(), resetUrl, "Password Reset Request", "");
            return response("Password reset email sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
            return response("Error password reset sending email!");
        }
    }

    @Transactional(readOnly = false)
    public StructureRS handlePasswordReset(String token,String newPassword){
        Optional<PasswordResetTokenEntity> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken.isEmpty()) throw new BadRequestException("Invalid Token");
        UserEntity user = passwordResetToken.get().getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken.get());
        return response("Password reset successful!");
    }
    
}
