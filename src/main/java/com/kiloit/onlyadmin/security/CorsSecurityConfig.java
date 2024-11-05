package com.kiloit.onlyadmin.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;

/**
 * @author Sombath
 * create at 24/2/23 3:54 AM
 */

@Configuration
@ConfigurationProperties(prefix = "spring.cors")
@Data
@EnableMethodSecurity
public class CorsSecurityConfig {
    private List<String> allowedOrigins;
    private List<String> allowedHeader;
    private List<String> allowedMethod;
}
