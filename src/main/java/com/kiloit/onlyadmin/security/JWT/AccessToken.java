package com.kiloit.onlyadmin.security.JWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Component
public class AccessToken {
    
    @Bean("accessTokenKeypaPair")
    KeyPair accessTokenKeypaPair() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPaireGenerator = KeyPairGenerator.getInstance("RSA");
        keyPaireGenerator.initialize(2048);
        return keyPaireGenerator.generateKeyPair();
    }

    @Bean("accessTokenRSAKey")
    RSAKey accessTokenRSAKey(@Qualifier("accessTokenKeypaPair")KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
               .privateKey(keyPair.getPrivate())
               .keyID(UUID.randomUUID().toString())
               .build();
    }

    @Bean("accessTokenJwtDecoder")
    JwtDecoder accessTokenJwtDecoder(@Qualifier("accessTokenRSAKey")RSAKey rsaKey) throws JOSEException{
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean("accessTokenJwkSource")
    JWKSource <SecurityContext> accessTokenJwkSource(@Qualifier("accessTokenRSAKey")RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector,securityContext)-> jwkSelector.select(jwkSet);
    }

    @Bean("accessTokenJwtEncoder")
    JwtEncoder accessTokenJwtEncoder(@Qualifier("accessTokenJwkSource")JWKSource<SecurityContext> jwkSource){
        return new  NimbusJwtEncoder(jwkSource);
    }
}