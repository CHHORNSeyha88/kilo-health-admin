package com.kiloit.onlyadmin.service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.entity.UserVerification;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.database.repository.UserVerificationRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.mapper.UserMapper;
import com.kiloit.onlyadmin.model.request.auth.LoginRequest;
import com.kiloit.onlyadmin.model.request.auth.RefreshTokenRequest;
import com.kiloit.onlyadmin.model.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.request.auth.SendVerificationRequest;
import com.kiloit.onlyadmin.model.request.auth.VerificationRequest;
import com.kiloit.onlyadmin.model.respone.auth.AuthResponse;
import com.kiloit.onlyadmin.model.respone.auth.RegisterResponse;
import com.kiloit.onlyadmin.util.RandomUntil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.nio.file.OpenOption;
import java.time.*;
import java.util.List;
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

    @Value("${spring.mail.username}")
    private String adminEmail;

    public StructureRS register(RegisterRequest registerRequest) {
        // Validate Phone Number
        if(userRepository.existsByPhone(registerRequest.phone())){
            throw new BadRequestException("Phone Number has been already exist...");
        }

        // Validate Email
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new BadRequestException("Emaile has been already exist...");
        }

        // Validate user's Password
        if(!registerRequest.confirmPassword().equals(registerRequest.password())){
            throw new BadRequestException("Password has not been match...");
        }

        UserEntity user = userMapper.fromRegisterRequest(registerRequest);
        Optional<RoleEntity> role = roleRepository.findById(Long.parseLong("3"));

        // Validate role's user
        if(role.isEmpty()) throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role.get());
        user.setCreatedAt(Instant.now());
        user.setModifiedAt(Instant.now());

        userRepository.save(user);

        return response(RegisterResponse.builder()
        .message("You register has been successfully...")
        .email(user.getEmail()).build());
    }

    public StructureRS sendVerification(String email) throws MessagingException{

        //Validate email
        UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,MessageConstant.USER.USER_NOT_FOUND)
                    );

        String codeRandom= RandomUntil.random6Digits();
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(codeRandom);
        userVerification.setExpiryTime(LocalTime.now().plusSeconds(60));
 
        userVerificationRepository.save(userVerification);

        return response(prepareTemplateMail(email, user, codeRandom));

    }
    
    public StructureRS prepareTemplateMail(String email,UserEntity user,String codeRandom){

        Map<String,Object> templateModel= new HashMap<String, Object>();
        Context context = new Context();
        try {

            // Prepare model data for template
            templateModel.put("userName", user.getUsername());
            templateModel.put("userEmail", user.getEmail());
            templateModel.put("registrationDate", new Date());
            templateModel.put("code",codeRandom);
            context.setVariables(templateModel);

            // Process Thymeleaf template
            String htmlContent = templateEngine.process("email-template", context);

            // Prepare email for sending
            prepareMailSend(email, htmlContent);

            return response("Email sent!");
        } catch (MessagingException e) {
            e.printStackTrace();
            return response("Error sending email!");
        }
    }

    public void prepareMailSend(String toMail,String htmlContent) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);

        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject("User Verification");
        mimeMessageHelper.setText(htmlContent,true);
        javaMailSender.send(message);
    }

    public void verify(VerificationRequest verificationRequest) {

          //Validate email
          UserEntity user = userRepository.findByEmail(verificationRequest.email())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,MessageConstant.USER.USER_NOT_FOUND)
          );

          //Validate verified code
          UserVerification verification = userVerificationRepository.findByUserAndVerifiedCode(user,verificationRequest.verifiedCode())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,MessageConstant.USER.USER_NOT_FOUND)
          );

          //Is verified code expired
          if(LocalTime.now().isAfter(verification.getExpiryTime())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Verified code has expired");
          }

          user.setIsVerification(true);
          userRepository.save(user);

          userVerificationRepository.delete(verification);

    }

    public StructureRS resendVerification(SendVerificationRequest sendVerificationRequest) {
          //Validate email
          UserEntity user = userRepository.findByEmail(sendVerificationRequest.email())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,MessageConstant.USER.USER_NOT_FOUND)
          );

          //Validate verified code
          UserVerification verification = userVerificationRepository.findByUser(user)
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,MessageConstant.USER.USER_NOT_FOUND)
          );

          String codeRandom= RandomUntil.random6Digits();

          verification.setVerifiedCode(codeRandom);
          verification.setExpiryTime(LocalTime.now().plusSeconds(60));
          userVerificationRepository.save(verification);

          return response(prepareTemplateMail(user.getEmail(), user, codeRandom));
    }

    public StructureRS login(LoginRequest loginRequest) {
        Authentication authentication= new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
        authentication=daoAuthenticationProvider.authenticate(authentication);

        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        // Create AccessToken
        String accessToken =  createToken(accessTokenJwtEncoder, createClaimsSet(authentication,scope,"dao"));
        
        // Create RefreshToken
        String refreshToken = createToken(refreshTokenJwtEncoder, createClaimsSet(authentication,scope,"dao-jwt"));

        return response(AuthResponse.builder()
                            .accessToken(accessToken)
                            .refeshToken(refreshToken)
                            .tokenType("Bearer")
                            .build());
    }

    public StructureRS refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String accessToken;
        String refreshToken= refreshTokenRequest.refreshToken();
        Authentication authentication = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        authentication=jwtAuthenticationProvider.authenticate(authentication);

        long remainingDays = Duration.between(Instant.now(), ((Jwt)authentication.getPrincipal()).getExpiresAt()).toDays();

        if(remainingDays<=1){
            refreshToken=createToken(accessTokenJwtEncoder, createClaimsSet(authentication, null,"jwt"));
        }

        accessToken = createToken(accessTokenJwtEncoder, createClaimsSet(authentication,null,"jwt"));
        return response(AuthResponse.builder()
                            .tokenType("Bearer")
                            .accessToken(accessToken)
                            .refeshToken(refreshToken)
                            .build());
    }

    public String createToken(JwtEncoder tokentJwtEncoder,JwtClaimsSet jwtClaimsSet){
        return  tokentJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }

    public JwtClaimsSet createClaimsSet(Authentication authentication,String scope,String type){
        
        Instant expired=Instant.now().plus(30,ChronoUnit.MINUTES);
        String id =authentication.getName();
        String subject_="Access APIs";
        String issuer=id;
        Instant issueAt=Instant.now();
        List<String> audience=List.of("NextJs","Android","IOS");
        Boolean claimValue1=true;
        String claimValue2=scope;

        if(type=="jwt"){
            Jwt jwt= (Jwt)authentication.getPrincipal();
            id=jwt.getId();
            issuer=id;
            expired=Instant.now().plus(30,ChronoUnit.DAYS);
            claimValue2=jwt.getClaimAsString("scope");
            audience=jwt.getAudience();
        }
        else{
            if(type=="dao-jwt"){
                expired= Instant.now().plus(30,ChronoUnit.DAYS);
            }
        }

        return JwtClaimsSet.builder()
        .id(id)
        .subject(subject_)
        .issuer(issuer)
        .issuedAt(issueAt)
        .expiresAt(expired)
        .audience(audience)
        .claim("isAdmin", claimValue1)
        .claim("scope", claimValue2)
        .build();
    }
    
}
