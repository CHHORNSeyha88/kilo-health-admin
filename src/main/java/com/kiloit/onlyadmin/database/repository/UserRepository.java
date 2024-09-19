package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
