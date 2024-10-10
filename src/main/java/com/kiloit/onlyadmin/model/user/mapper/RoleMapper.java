package com.kiloit.onlyadmin.model.user.mapper;

import org.mapstruct.Mapper;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.model.role.response.RoleRS;

@Mapper(componentModel="spring")
public interface RoleMapper {
    
    RoleRS formRoleEntity(RoleEntity roleEntity);
}
