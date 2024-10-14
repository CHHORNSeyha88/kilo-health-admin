package com.kiloit.onlyadmin.model.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.model.role.response.PermissionRS;
import com.kiloit.onlyadmin.model.user.request.UserRQ;
import com.kiloit.onlyadmin.model.user.request.UserUpdateRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.user.respone.UserDetailRS;

@Mapper(componentModel="spring")
public interface UserMapper {
    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "isVerification",ignore = true),
        @Mapping(target="topics",ignore = true),
        @Mapping(target = "postEntities",ignore = true),
        @Mapping(target="postViews",ignore=true),
        @Mapping(target="role",ignore=true)
    })
    UserEntity fromUser(UserRQ request);

    @Mapping(target = "status",ignore = true)
    PermissionRS from(PermissionEntity permissionEntity);
    UserDetailRS fromUserList(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "isVerification",ignore = true),
        @Mapping(target="topics",ignore = true),
        @Mapping(target = "postEntities",ignore = true),
        @Mapping(target="postViews",ignore=true),
        @Mapping(target="role",ignore=true),
        @Mapping(target = "email",ignore = true),
        @Mapping(target="password",ignore=true),
        @Mapping(target="username",ignore=true),
        @Mapping(target = "role.id",source = "roleId")
    })
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest,@MappingTarget UserEntity userEntity);


    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "isVerification",ignore = true),
        @Mapping(target="topics",ignore = true),
        @Mapping(target = "postEntities",ignore = true),
        @Mapping(target="postViews",ignore=true),
        @Mapping(target="role",ignore=true),
        @Mapping(target = "email",ignore = true),
        @Mapping(target="password",ignore=true),
        @Mapping(target="username",ignore=true),
        @Mapping(target="dob",ignore=true),
        @Mapping(target="firstname",ignore=true),
        @Mapping(target = "lastname",ignore = true),
        @Mapping(target="address",ignore=true),
        @Mapping(target="photo",ignore=true)
    })
    UserEntity fromRegisterRequest(RegisterRequest registerRequest);

}
