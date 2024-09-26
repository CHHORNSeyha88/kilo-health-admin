package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.database.specification.UserSpecification;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.mapper.UserMapper;
import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.model.respone.UserDetailRS;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    public StructureRS list(BaseListingRQ request){
        Page<UserEntity> list = userRepository.findAll(
                UserSpecification.filter(request.getQuery()),
                request.getPageable(request.getSort(), request.getOrder())
        );
        return response(list.getContent().stream().map(mapper::fromUserList).toList(), list);
    }
    public StructureRS detail(Long id) {
        Optional<UserEntity> user = userRepository.findByIdAndDeletedDateNull(id);
        if (user.isEmpty())
            throw new BadRequestException("User is not found!!");
        return response(mapper.fromUserList(user.get()));
    }
//    public StructureRS create(UserRQ request) {
//        UserEntity users = mapper.fromUser(request);
//        return response(mapper.fromUserList(userRepository.save(users)));
//    }
//@Transactional
//public StructureRS create(UserRQ request) {
//    this.createUpdate(new UserEntity(), request);
//    return response();
//}
//
//    private void createUpdate(UserEntity userEntity, UserRQ userResourceRQ) {
//
//        RoleEntity roleEntity = roleRepository.getReferenceById(userResourceRQ.getRoleId());
//
//        userEntity.setPhoto(userResourceRQ.getPhoto());
//        userEntity.setFirstname(userResourceRQ.getFirstname());
//        userEntity.setLastname(userResourceRQ.getLastname());
//        userEntity.setDoB(userResourceRQ.getDoB());
//        userEntity.setPhone(userResourceRQ.getPhone());
//
//
//        userRepository.save(userEntity);
//
//    }

    public StructureRS update(Long id, UserRQ request) {
        Optional<UserEntity> user = userRepository.findByIdAndDeletedDateNull(id);
        if (user.isEmpty())
            throw new BadRequestException("Product not found");
        UserEntity entity = user.get();
        if (request.getFirstname() != null)
            entity.setFirstname(request.getFirstname());
        if (request.getLastname() != null)
            entity.setLastname(request.getLastname());
        UserEntity savedUser = userRepository.save(entity);

        UserDetailRS response = new UserDetailRS();
        response.setPhoto(savedUser.getPhoto());
        response.setFirstname(savedUser.getFirstname());
        response.setLastname(savedUser.getLastname());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setAddress(savedUser.getAddress());

        return response(response);
    }
}
