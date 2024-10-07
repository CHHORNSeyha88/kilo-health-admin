package com.kiloit.onlyadmin.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.entity.UserVerification;
import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification,Long> {
    
    Optional<UserVerification> findByUserAndVerifiedCode(UserEntity user,String verifiedCode);
    Optional<UserVerification> findByUser(UserEntity user);
}
