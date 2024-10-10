package com.kiloit.onlyadmin.model.user.mapper;

import org.mapstruct.Mapper;

import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.model.respone.PermissionRS;

@Mapper(componentModel="spring")
public interface PermissionMapper {
    
    PermissionRS formPermissionEntity(PermissionEntity permissionEntity);
}
