package com.kiloit.onlyadmin.util;


import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    private static final String ISSUER = "KILO-IT";
    private final JwtEncoder encoder;

    public TokenUtils(JwtEncoder jwtEncoder) {
        this.encoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        return this.generateToken((UserPrincipal) authentication.getPrincipal());
    }

    public String generateToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();
        String scope = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        scope = scope.concat(" ").concat(userPrincipal.getRoleName());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(userPrincipal.getName())
                .id(userPrincipal.getId().toString())

                .claim("scope", scope)

                .claim("id", userPrincipal.getId())
                .claim("username", userPrincipal.getUsername() )
                .claim("roleId", userPrincipal.getRoleId() )
                .claim("email", userPrincipal.getEmail())
                .claim("phone", emptyIfNull(userPrincipal.getPhone()) )
                .claim("address", emptyIfNull(userPrincipal.getAddress()) )
                .claim("avatar", emptyIfNull(userPrincipal.getAvatar()) )
                .claim("roleName", emptyIfNull(userPrincipal.getRoleName()) )

                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    public String generateToken(UserEntity userPrincipal) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(userPrincipal.getUsername())
                .id(userPrincipal.getId().toString())

                .claim("id", userPrincipal.getId())
                .claim("username", userPrincipal.getUsername() )
                .claim("email", userPrincipal.getEmail())
                .claim("phone", emptyIfNull(userPrincipal.getPhone()) )
                .claim("address", emptyIfNull(userPrincipal.getAddress()) )

                .build();

        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    private String emptyIfNull(String field) {
        if (field == null)
            return "";
        return field;
    }

}
