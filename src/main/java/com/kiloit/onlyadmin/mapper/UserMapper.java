package com.kiloit.onlyadmin.mapper;

import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.model.respone.UserListRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kiloit.onlyadmin.database.entity.UserEntity;

@Mapper(componentModel="spring")
public interface UserMapper {
    
    @Mapping(target = "role.id",source = "roleId")
    UserEntity fromUser(UserRQ request);

    @Mapping(target = "roleId",source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserListRS fromUserList(UserEntity entity);


}
