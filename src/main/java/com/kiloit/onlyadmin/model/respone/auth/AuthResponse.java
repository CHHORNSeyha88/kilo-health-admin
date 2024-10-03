package com.kiloit.onlyadmin.model.respone.auth;

import lombok.Builder;

@Builder
public record AuthResponse(
    
    String tokenType,
    String accessToken,
    String refeshToken
){
    
}
