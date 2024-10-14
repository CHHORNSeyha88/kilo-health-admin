package com.kiloit.onlyadmin.service;

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
import com.kiloit.onlyadmin.model.role.response.PermissionRS;
import com.kiloit.onlyadmin.model.role.response.RoleRS;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

        List<Object[]> results = roleRepository.findByIdFetchPermission(Id);
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

    // Method to delete a role 
    public StructureRS delete(Long Id) {
        // Check exists
        RoleEntity roleEntity = roleRepository.findById(Id).orElseThrow(() ->
                new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND));
        // Delete  entity
        roleRepository.delete(roleEntity);
        return response(HttpStatus.OK, MessageConstant.ROLE.ROLE_DELETED_SUCCESSFULLY);
    }

    // Method to assign a permission to a role
    public StructureRS assignPermissionToRole(String roleName, String permissionName) {
        RoleEntity role = roleRepository.findByName(roleName);
        PermissionEntity permission = permissionRepository.findByName(permissionName);

        // Validate existing permission
        for(PermissionEntity permissionEntity:role.getPermissions()){
            if(permissionEntity.getId()== permission.getId()){
                return response("Permission has already been assign...");
            }
        }

        if (role != null && permission != null) {
            role.getPermissions().add(permission);
            roleRepository.save(role);
            return response("Permission has been assign successfully...");
        }
        throw new BadRequestException("Role or Permission has not been found");
    }

    // Method to remove a permission from a role
    @Transactional
    public StructureRS removePermissionFromRole(String roleName, String permissionName) {

        RoleEntity role = roleRepository.findByName(roleName);
        PermissionEntity permission = permissionRepository.findByName(permissionName);

        if (role != null && permission != null) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
            return response("Permission has been remove successfully...");
        }
        throw new BadRequestException("Role or Permission has not been found");
    }

    // Method to update a role's permissions (override existing ones)
    public StructureRS updatePermissionsForRole(String roleName, List<String> permissionNames) {
        RoleEntity role = roleRepository.findByName(roleName);

        if (role != null) {
            // Clear existing permissions
            role.getPermissions().clear();

            // Add new permissions
            for (String permissionName : permissionNames) {
                PermissionEntity permission = permissionRepository.findByName(permissionName);
                if (permission != null) {
                    role.getPermissions().add(permission);
                }
            }

            roleRepository.save(role);

            return response("Permission has been update successfully...");
        }
        throw new BadRequestException(MessageConstant.ROLE.ROLE_NOT_FOUND);
    }

}
