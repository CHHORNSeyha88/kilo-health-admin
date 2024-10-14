package com.kiloit.onlyadmin.model.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.model.role.response.PermissionRS;
import java.util.List;


@Mapper(componentModel="spring")
public interface PermissionMapper {
    
    @Mapping(target="status",ignore=true)
    PermissionRS formPermissionEntity(PermissionEntity permissionEntity);
    
    List<PermissionRS> fromPermissionList(List<PermissionEntity> listPermissionEntity);
}
