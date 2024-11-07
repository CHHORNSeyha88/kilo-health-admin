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
        Page<UserEntity> userEntitys = userRepository.findAll(UserSpecification.dynamicQuery(request.getQuery()),(PageRequest)(request.getPageable("id")));
        return response(userEntitys.map(userMapper::fromUserList).getContent(),userEntitys);
    }

    public StructureRS detail(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);    
        return response(userMapper.fromUserList(user.get()));
    }

    @Transactional
    public StructureRS create(UserRQ request) {
        RoleEntity roleEntity = roleRepository.findById(request.getRoleId()).orElseThrow(()->new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        if(userRepository.existsByEmail(request.getEmail())) throw new BadRequestException(MessageConstant.REGISTERPROPERTY.EMAIL_IS_EXISTING);
        if(userRepository.existsByUsername(request.getUsername())) throw new BadRequestException(MessageConstant.REGISTERPROPERTY.USERNAME_IS_NOT_VALID);
        if(userRepository.existsByPhone(request.getPhone())) throw new BadRequestException(MessageConstant.REGISTERPROPERTY.PHONE_IS_NOT_VALID);
        UserEntity userEntity = userMapper.fromUser(request);
        userEntity.setIsVerification(true);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(roleEntity);
        return response(userMapper.fromUserList(userRepository.save(userEntity)));
    }

    @Transactional
    public StructureRS update(Long id, UserUpdateRequest userUpdateRequest) throws BadRequestException {
        UserEntity user = userRepository.findById(id).orElseThrow(()->new BadRequestException(MessageConstant.USER.USER_NOT_FOUND));
        RoleEntity roleEntity = roleRepository.findById(userUpdateRequest.getRoleId()).orElseThrow(()-> new BadRequestException(MessageConstant.USER.USER_NOT_FOUND));
        user.setRole(roleEntity);
        userMapper.fromUserUpdateRequest(userUpdateRequest, user);
        return response(userMapper.fromUserList(userRepository.save(user)));
    }
    @Transactional
    public StructureRS delete(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);
        user.get().setDeletedAt(Instant.now());
        userRepository.save(user.get());
        return response(MessageConstant.USER.USER_HAS_BEEN_DELETED);
    }
}
