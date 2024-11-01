package com.kiloit.onlyadmin.service;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.database.entity.RoleEntity;
import com.kiloit.onlyadmin.database.repository.PermissionRepository;
import com.kiloit.onlyadmin.database.repository.RoleRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.model.role.mapper.PermissionMapper;
import com.kiloit.onlyadmin.model.role.mapper.RoleMapper;
import com.kiloit.onlyadmin.model.role.request.RoleRQ;
import com.kiloit.onlyadmin.model.role.request.RoleRequestUpdate;
import com.kiloit.onlyadmin.model.role.request.SetPermissionItemRequest;
import com.kiloit.onlyadmin.model.role.request.SetPermissionRequest;
import com.kiloit.onlyadmin.model.role.response.PermissionStatusResponse;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServices extends BaseService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    @Transactional
    public StructureRS getAll(BaseListingRQ request) {
        Page<RoleEntity> roleEntities = roleRepository.findByNameContainsOrderByNameAsc(request.getQuery(), request.getPageable("id"));
        return response(roleEntities.map(roleMapper::toRoleListResponse).getContent(),roleEntities);
    }

    @Transactional
    public StructureRS getRoleById(Long Id) {
        Optional<RoleEntity> roleEntity = roleRepository.findByIdFetchPermission(Id);
        if(roleEntity.isEmpty()) throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        return response(roleMapper.fromRoleEntity(roleEntity.get()));
    }

    @Transactional
    public StructureRS create(RoleRQ roleRQ) {
        RoleEntity roleEntity = roleMapper.fromRequest(roleRQ);
        roleEntity.setCreatedAt(Instant.now());
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_CREATED_SUCCESSFULLY,roleMapper.toRoleCreateResponse(roleRepository.save(roleEntity)));
    }

    @Transactional
    public StructureRS update(Long id,RoleRequestUpdate roleRequestUpdate) {
        RoleEntity roleEntity = roleRepository.findByIdAndDeletedAtNull(id).orElseThrow(() -> new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        roleMapper.fromRoleRequestUpdate(roleRequestUpdate, roleEntity);
        roleRepository.save(roleEntity);
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
    }

    @Transactional
    public StructureRS delete(Long Id) {
        RoleEntity roleEntity = roleRepository.findByIdAndDeletedAtNull(Id).orElseThrow(() -> new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        roleEntity.setDeletedAt(Instant.now());
        roleRepository.save(roleEntity);
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_DELETED_SUCCESSFULLY);
    }

    @Transactional
    public StructureRS setPermission(SetPermissionRequest setPermissionRequest) {
        Optional<RoleEntity> roleEntity = roleRepository.findByIdFetchPermission(setPermissionRequest.getRoleId());
        if (roleEntity.isEmpty()) return response(HttpStatus.NOT_FOUND, MessageConstant.ROLE.ROLE_NOT_FOUND);

        Set<Long> requestedPermissionIds = setPermissionRequest.getItems().stream().map(SetPermissionItemRequest::getId).collect(Collectors.toSet());
        Set<PermissionEntity> requestedPermissions = permissionRepository.findAllByIdIn(requestedPermissionIds);

        if (requestedPermissions.isEmpty()) return response(HttpStatus.BAD_REQUEST, "Permission has not been found");
        Set<Long> removeIds = setPermissionRequest.getItems().stream().filter(item -> !item.getStatus()).map(SetPermissionItemRequest::getId).collect(Collectors.toSet());
        Set<PermissionEntity> toRemove = requestedPermissions.stream().filter(permission -> removeIds.contains(permission.getId())).collect(Collectors.toSet());
        
        roleEntity.get().getPermissions().addAll(requestedPermissions);
        roleEntity.get().getPermissions().removeAll(toRemove);
        roleRepository.save(roleEntity.get());
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
    }

    @Transactional
    public StructureRS listAllPermissions(BaseListingRQ request,Long roleId,String module){
        if(roleId!=null && module!=null){
            roleId=null;
            module=null;
        }
        Page<PermissionStatusResponse> listPermission = permissionRepository.findAllPermission(roleId,module,request.getPageable());
        return response(listPermission.getContent(),listPermission);
    }

}
