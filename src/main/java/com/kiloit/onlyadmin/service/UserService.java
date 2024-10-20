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
import com.kiloit.onlyadmin.model.user.request.UserRQ;
import com.kiloit.onlyadmin.model.user.request.UserUpdateRequest;
import com.kiloit.onlyadmin.model.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public StructureRS list(BaseListingRQ request){
        Specification<UserEntity> specification = UserSpecification.hasNotBeenDeleted().and(UserSpecification.dynamicQuery(request.getQuery()));
        Page<UserEntity> userEntitys = userRepository.findAll(specification,(PageRequest)(request.getPageable("id")));
        return response(userEntitys.map(userMapper::fromUserList).getContent(),userEntitys);
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

        // Validate email
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email has already existing...");
        }

        // Validate username
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("User name not valid...");
        }
        UserEntity userEntity = userMapper.fromUser(request);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
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

    public StructureRS delete(Long id){
        Optional<UserEntity> user = userRepository.findByIdAndDeletedAtNull(id);
        if (user.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);

        user.get().setDeletedAt(Instant.now());
        userRepository.save(user.get());
        
        return response(MessageConstant.USER.USER_HAS_BEEN_DELETED);
    }
}
