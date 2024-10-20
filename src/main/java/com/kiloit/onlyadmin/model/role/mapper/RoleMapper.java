package com.kiloit.onlyadmin.model.role.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.model.role.request.RoleRQ;
import com.kiloit.onlyadmin.model.role.request.RoleRequestUpdate;
import com.kiloit.onlyadmin.model.role.response.PermissionRS;
import com.kiloit.onlyadmin.model.role.response.RoleListResponse;
import com.kiloit.onlyadmin.model.role.response.RoleRS;

@Mapper(componentModel="spring")
public interface RoleMapper {

    RoleRS fromRoleEntity(RoleEntity roleEntity);

    @Mapping(target = "status",ignore = true)
    PermissionRS from(PermissionEntity permissionEntity);

    @Mappings({
      @Mapping(target = "createdAt",ignore = true),
      @Mapping(target = "deletedAt",ignore = true),
      @Mapping(target="id",ignore = true),
      @Mapping(target = "modifiedAt",ignore = true),
      @Mapping(target="users",ignore=true),
      @Mapping(target = "permissions",ignore = true)
    })
    RoleEntity fromRequest(RoleRQ roleRQ);

    @Mappings({
      @Mapping(target = "createdAt",ignore = true),
      @Mapping(target = "deletedAt",ignore = true),
      @Mapping(target="id",ignore = true),
      @Mapping(target = "modifiedAt",ignore = true),
      @Mapping(target="users",ignore=true),
      @Mapping(target = "permissions",ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromRoleRequestUpdate(RoleRequestUpdate roleRequestUpdate,@MappingTarget RoleEntity roleEntity);

    RoleListResponse toRoleListResponse(RoleEntity roleEntity);
}
