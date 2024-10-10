package com.kiloit.onlyadmin.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(
    @NotBlank(message = "ResfreshToken is required")
    String refreshToken
) {
    
}
