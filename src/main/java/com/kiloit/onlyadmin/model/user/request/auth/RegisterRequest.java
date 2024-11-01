package com.kiloit.onlyadmin.model.user.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank(message = "Phone Number is required")
    @Size(min = 9,max = 10,message = "Phone Number must be between 9 to 10 digit")
    String phone,

    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,12}$"
    ,message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter")
    String password,

    @Size(min=6,message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,12}$"
    ,message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter")
    String confirmPassword,

    @NotBlank(message = "Name is required")
    String username,

    @NotBlank(message = "Gender is required")
    String gender,
    
    @NotNull
    LocalDate dob
    ){

    }
