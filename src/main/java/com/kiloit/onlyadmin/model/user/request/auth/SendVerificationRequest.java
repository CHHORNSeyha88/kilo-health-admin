package com.kiloit.onlyadmin.model.request.auth;

import jakarta.validation.constraints.NotBlank;

public record SendVerificationRequest(
    @NotBlank(message = "Email is required")
    String email
) {
    
}
