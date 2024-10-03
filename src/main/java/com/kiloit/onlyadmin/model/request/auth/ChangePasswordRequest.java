package com.kiloit.onlyadmin.model.request.auth;

public record ChangePasswordRequest(
    String oldPassword,
    String newPassword,
    String confirmPassword
) {
    
}
