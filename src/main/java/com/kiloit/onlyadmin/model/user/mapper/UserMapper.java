package com.kiloit.onlyadmin.model.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.model.user.request.UserRQ;
import com.kiloit.onlyadmin.model.user.request.UserUpdateRequest;
import com.kiloit.onlyadmin.model.user.request.auth.RegisterRequest;
import com.kiloit.onlyadmin.model.user.respone.UserDetailRS;

@Mapper(componentModel="spring")
public interface UserMapper {
    
    @Mapping(target = "dob",source = "dob")
    UserEntity fromUser(UserRQ request);

    UserDetailRS fromUserList(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "role.id",source = "roleId")
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest,@MappingTarget UserEntity userEntity);

    UserEntity fromRegisterRequest(RegisterRequest registerRequest);

}
