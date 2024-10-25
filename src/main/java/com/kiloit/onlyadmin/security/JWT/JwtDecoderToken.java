package com.kiloit.onlyadmin.security.JWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class JwtDecoderToken {
   public Map<String, Object> getCliamSet() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication.getPrincipal() instanceof Jwt jwt) return jwt.getClaims();
       return null;
   }
}
