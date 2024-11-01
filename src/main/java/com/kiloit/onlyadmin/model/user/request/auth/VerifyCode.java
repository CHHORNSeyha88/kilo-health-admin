package com.kiloit.onlyadmin.model.user.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerifyCode(
    @NotBlank(message = "Code is required")
    String code,

    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,12}$"
    ,message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter")
    String password
){}
