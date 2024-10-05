package com.kiloit.onlyadmin.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    DaoAuthenticationProvider configureDaoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProvider configAuthenticationProvider(@Qualifier("refreshTokenJwtDecoder")JwtDecoder refreshTokenJwtDecoder){
        return new JwtAuthenticationProvider(refreshTokenJwtDecoder);
    }

    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity,@Qualifier("accessTokenJwtDecoder") JwtDecoder jwtDecoder) throws Exception{

        // httpSecurity.authorizeHttpRequests(endpoint-> endpoint

        // .requestMatchers("api/v1/auth/**")
        // .permitAll()

        // .requestMatchers("api/v1/users/**")
        // .hasAuthority("SCOPE_ROLE_Administrator")

        // .anyRequest().authenticated());
        // httpSecurity.csrf(token -> token.disable());

        // httpSecurity.oauth2ResourceServer(jwt->jwt.jwt(jwtConfigurer->jwtConfigurer.decoder(jwtDecoder)));

        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return httpSecurity.build();
    }
    
}
