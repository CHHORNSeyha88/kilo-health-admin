package com.kiloit.onlyadmin.security;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.Optional;

/**
 * @author Sombath
 * create at 13/9/23 11:55 PM
 */

class SpringSecurityAuditorAware implements AuditorAware<UserEntity> {

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> attributes;
        if (authToken instanceof JwtAuthenticationToken) {
            attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
            UserEntity user = new UserEntity();

            user.setId((Long) attributes.get("id"));
            user.setUsername((String) attributes.get("username"));
            user.setEmail((String) attributes.get("email"));
            user.setPhone((String) attributes.get("phone"));

            return Optional.of(user);
        }
        return Optional.empty();
    }

}


