package com.kiloit.onlyadmin.model.role.mapper;

import org.mapstruct.Mapper;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.model.role.request.RoleRQ;
import com.kiloit.onlyadmin.model.role.response.RoleRS;

@Mapper(componentModel="spring")
public interface RoleMapper {
    RoleRS fromRoleEntity(RoleEntity roleEntity);
    RoleEntity fromRequest(RoleRQ roleRQ);
}