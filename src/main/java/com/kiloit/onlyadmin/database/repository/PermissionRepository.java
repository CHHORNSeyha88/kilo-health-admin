package com.kiloit.onlyadmin.database.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kiloit.onlyadmin.database.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Long>{

    PermissionEntity findByName(String permissionName);
    Set<PermissionEntity> findAllByIdIn(Set<Long> ids);
    
}
