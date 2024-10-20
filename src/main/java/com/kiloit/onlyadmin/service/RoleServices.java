package com.kiloit.onlyadmin.service;

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
import com.kiloit.onlyadmin.model.role.response.PermissionRS;
import com.kiloit.onlyadmin.model.role.response.RoleRS;
import java.util.List;
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

    // Method to get all roles
    public StructureRS getAll(BaseListingRQ request) {
        Page<RoleEntity> roleEntities = roleRepository.findByNameContainsOrderByNameAsc(request.getQuery(), request.getPageable("id"));
        return response(roleEntities.map(roleMapper::toRoleListResponse).getContent(),roleEntities);
    }
    
    // Method to get a role by id
    public StructureRS getRoleById(Long Id) {

        List<Object[]> results = roleRepository.findByIdFetchPermissions(Id);
        if(results.isEmpty()){
            throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
        }

        // Validate list permissions
        List<PermissionRS> listPermission = permissionMapper.fromPermissionList(permissionRepository.findAll());
        if(listPermission.isEmpty()){
            throw new BadRequestException("List permission is empty...");
        }

        for(Object[] result: results){
          listPermission.stream().map(permission -> {
            PermissionRS per = permission;
            if(per.getId()==result[4]){
                per.setStatus(true);
            }
            return per;
          }).toList();
        }

        RoleRS role = new RoleRS();
        role.setId((Long)results.getFirst()[0]);
        role.setCode((String)results.getFirst()[1]);
        role.setName((String)results.getFirst()[2]);
        role.setModule((String)results.getFirst()[3]);
        role.setPermissions(listPermission);

        return response(role);
    }

    // Method to create a role
    public StructureRS create(RoleRQ roleRQ) {
        RoleEntity roleEntity = roleMapper.fromRequest(roleRQ);
        // Save  entity
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_CREATED_SUCCESSFULLY,roleRepository.save(roleEntity));
    }

    public StructureRS update(Long id,RoleRequestUpdate roleRequestUpdate) {
        // Check if the tag exists
        RoleEntity roleEntity = roleRepository
                                .findById(id)
                                .orElseThrow(() -> new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));

        // Update the tag entity
        roleMapper.fromRoleRequestUpdate(roleRequestUpdate, roleEntity);
        // Save the updated tag entity
        roleRepository.save(roleEntity);

        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
    }

    // Method to delete a role 
    public StructureRS delete(Long Id) {
        // Check if the tag exists
        RoleEntity roleEntity = roleRepository.findById(Id).orElseThrow(() ->
                new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        // Delete the tag entity
        roleRepository.delete(roleEntity);
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_DELETED_SUCCESSFULLY);
    }

    @Transactional
    public StructureRS setPermission(SetPermissionRequest setPermissionRequest) {
    
    // Fetch role entity with permissions
    RoleEntity roleEntity = roleRepository.findByIdFetchPermission(setPermissionRequest.getRoleId());
    if (roleEntity == null) {
        return response(HttpStatus.NOT_FOUND, MessageConstant.ROLE.ROLE_NOT_FOUND);
    }

    // Extract permission IDs from the request
    Set<Long> requestedPermissionIds = setPermissionRequest.getItems().stream()
            .map(SetPermissionItemRequest::getId)
            .collect(Collectors.toSet());

    // Fetch permission entities based on request IDs
    Set<PermissionEntity> requestedPermissions = permissionRepository.findAllByIdIn(requestedPermissionIds);
    if (requestedPermissions.isEmpty()) {
        return response(HttpStatus.BAD_REQUEST, "Permission has not been found");
    }

    // Separate permissions to remove based on status
    Set<Long> removeIds = setPermissionRequest.getItems().stream()
            .filter(item -> !item.getStatus()) // Filter items with status false
            .map(SetPermissionItemRequest::getId)
            .collect(Collectors.toSet());

    Set<PermissionEntity> toRemove = requestedPermissions.stream()
            .filter(permission -> removeIds.contains(permission.getId()))
            .collect(Collectors.toSet());

    // Update the role's permission se
    roleEntity.getPermissions().addAll(requestedPermissions); // Add all requested permissions
    roleEntity.getPermissions().removeAll(toRemove); // Remove unwanted permissions

    // Save the updated role entity
    roleRepository.save(roleEntity);

    return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
}


}
