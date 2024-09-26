package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> ,JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByIdAndDeletedDateNull(Long id);
}
