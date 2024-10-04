package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> ,JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByIdAndDeletedAtNull(Long id);

    boolean existsByPhone(
            String phoneNumber);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phoneNumber);

    @Query("SELECT u FROM UserEntity AS u WHERE email=:userName OR username=:userName")
    Optional<UserEntity> findUserOrEmail(@Param("userName") String userName);
}
