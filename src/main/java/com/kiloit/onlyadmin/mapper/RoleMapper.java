package com.kiloit.onlyadmin.mapper;

import org.mapstruct.Mapper;

import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.model.respone.RoleRS;

@Mapper(componentModel="spring")
public interface RoleMapper {
    
    RoleRS formRoleEntity(RoleEntity roleEntity);
}
