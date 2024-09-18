package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
