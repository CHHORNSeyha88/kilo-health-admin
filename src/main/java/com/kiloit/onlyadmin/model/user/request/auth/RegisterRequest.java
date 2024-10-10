package com.kiloit.onlyadmin.model.user.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Phone Number is required")
    @Size(min = 9,max = 10,message = "Phone Number must be between 9 to 10 digit")
    String phone,
    @NotBlank(message = "Email is required")
    String email,
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$")
    String password,
    @NotBlank(message = "Confirm Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$")
    String confirmPassword,
    @NotBlank(message = "Name is required")
    String username,
    @NotBlank(message = "Gender is required")
    String gender
    ){

    }
