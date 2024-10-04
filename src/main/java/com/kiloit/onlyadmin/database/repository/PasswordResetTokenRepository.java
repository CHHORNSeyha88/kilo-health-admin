package com.kiloit.onlyadmin.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiloit.onlyadmin.database.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity,Long>{

    Optional<PasswordResetTokenEntity> findByToken(String token);
    
}
