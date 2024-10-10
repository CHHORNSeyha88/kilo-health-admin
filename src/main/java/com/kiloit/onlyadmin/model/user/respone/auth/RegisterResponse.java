package com.kiloit.onlyadmin.model.respone.auth;

import lombok.Builder;

@Builder
public record RegisterResponse(
    String message,
    String email
) {
    
}
