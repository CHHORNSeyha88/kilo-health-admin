package com.kiloit.onlyadmin.database.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.kiloit.onlyadmin.database.entity.PasswordResetEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;

public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity,Long>{

    @Query("SELECT p FROM PasswordResetEntity AS p WHERE p.expiryTime > NOW()")
    Optional<PasswordResetEntity> findByCode(String verifyCode);

    PasswordResetEntity findByUser(UserEntity user);
    
}
