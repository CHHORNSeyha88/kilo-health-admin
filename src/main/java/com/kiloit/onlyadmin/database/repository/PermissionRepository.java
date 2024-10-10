package com.kiloit.onlyadmin.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiloit.onlyadmin.database.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity,Long>{

    PermissionEntity findByName(String permissionName);
    
}
