package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.database.specification.UserSpecification;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.mapper.UserMapper;
import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.model.request.UserUpdateRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public StructureRS list(BaseListingRQ request){
        Specification<UserEntity> specification = UserSpecification.filter(request.getQuery());
        Page<UserEntity> userEntitys = userRepository.findAllByDeletedAtNull(specification,(PageRequest)request.getPageable("id"));
        return response(userEntitys.map(userMapper::fromUserList),userEntitys);
    }

    public StructureRS detail(Long id) {
        Optional<UserEntity> user = userRepository.findByIdAndDeletedAtNull(id);
        
        // Validate user id
        if (user.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);    
        return response(userMapper.fromUserList(user.get()));
    }

    @Transactional
    public StructureRS create(UserRQ request) {
        
        // Validate role id
        RoleEntity roleEntity = roleRepository.
                                findById(request.getRoleId()).
                                orElseThrow(()->new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));

        UserEntity userEntity = userMapper.fromUser(request);
        userEntity.setRole(roleEntity);
        return response(userMapper.fromUserList(userRepository.save(userEntity)));
    }

    public StructureRS update(Long id, UserUpdateRequest userUpdateRequest) throws BadRequestException {

        // Validate user id
        UserEntity user = userRepository
                          .findByIdAndDeletedAtNull(id)
                          .orElseThrow(()->new BadRequestException(MessageConstant.USER.USER_NOT_FOUND));
        
        // Validate role id
        RoleEntity roleEntity = roleRepository
                                .findById(userUpdateRequest.getRoleId())
                                .orElseThrow(()-> new BadRequestException(MessageConstant.USER.USER_NOT_FOUND));
        user.setRole(roleEntity);
        userMapper.fromUserUpdateRequest(userUpdateRequest, user);

        return response(userMapper.fromUserList(userRepository.save(user)));
    }

    public void delete(Long id){
        Optional<UserEntity> user = userRepository.findByIdAndDeletedAtNull(id);
        if (user.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);

        user.get().setDeletedAt(Instant.now());
        userRepository.save(user.get());
    }
}
