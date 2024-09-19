package com.kiloit.onlyadmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.model.respone.UserRS;

@Mapper(componentModel="spring")
public interface UserMapper {
    
    @Mapping(source="role.id",target="roleId")
    UserRS fromUserEntity(UserEntity userEntity);
}
