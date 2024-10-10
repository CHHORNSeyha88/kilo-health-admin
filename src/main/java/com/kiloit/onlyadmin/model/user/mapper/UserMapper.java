package com.kiloit.onlyadmin.model.user.mapper;

import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.model.request.UserUpdateRequest;
import com.kiloit.onlyadmin.model.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.respone.UserListRS;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.kiloit.onlyadmin.database.entity.UserEntity;

@Mapper(componentModel="spring")
public interface UserMapper {
    
    @Mapping(target = "dob",source = "dob")
    UserEntity fromUser(UserRQ request);

    @Mapping(target = "roleId",source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserListRS fromUserList(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role.id",source = "roleId")
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest,@MappingTarget UserEntity userEntity);

    UserEntity fromRegisterRequest(RegisterRequest registerRequest);

}
