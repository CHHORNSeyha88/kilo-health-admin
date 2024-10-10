package com.kiloit.onlyadmin.model.user.respone.auth;

import lombok.Builder;

@Builder
public record RegisterResponse(
    String message,
    String email
) {
    
}
