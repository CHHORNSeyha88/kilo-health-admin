package com.kiloit.onlyadmin.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.user.request.auth.LoginRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RefreshTokenRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.user.request.auth.ResetPasswordRequest;
import com.kiloit.onlyadmin.model.user.request.auth.SendVerificationRequest;
import com.kiloit.onlyadmin.model.user.request.auth.VerificationRequest;
import com.kiloit.onlyadmin.model.user.request.auth.VerifyCode;
import com.kiloit.onlyadmin.service.AuthServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    
    private final AuthServices authService;

    @PostMapping("/refresh-token")
    public ResponseEntity<StructureRS> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return response(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<StructureRS> login(@Valid @RequestBody LoginRequest loginRequest){
        return response(authService.login(loginRequest));
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StructureRS> register(@Valid @RequestBody RegisterRequest registerRequest){
        return response(authService.register(registerRequest));
    }

    @PostMapping("/send-verification")
    public ResponseEntity<StructureRS> verification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException{
        return response(authService.sendVerification(sendVerificationRequest.email()));
    }

    @PostMapping("/resend-verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<StructureRS> resendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException{
        return response(authService.resendVerification(sendVerificationRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/verify")
    public ResponseEntity<StructureRS> verify(@Valid @RequestBody VerificationRequest verificationRequest) throws MessagingException{
        return response(authService.verify(verificationRequest));
    }

    @PostMapping("/reset-request")
    public ResponseEntity<StructureRS> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        return response(authService.resetPassword(resetPasswordRequest));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<StructureRS> handlePasswordReset(@Valid @RequestBody VerifyCode verifyCode){
        return response(authService.handlePasswordReset(verifyCode));
    }
}
