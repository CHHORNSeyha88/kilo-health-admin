package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByIdAndDeletedAtNull(Long id);

    RoleEntity findByUsers(UserEntity userEntity);
}
