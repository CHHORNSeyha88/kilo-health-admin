package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.role.request.RoleRQ;
import com.kiloit.onlyadmin.model.role.request.RoleRequestUpdate;
import com.kiloit.onlyadmin.model.role.request.SetPermissionRequest;
import com.kiloit.onlyadmin.service.RoleServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleServices roleServices;

    @Secured({PermissionConstant.ROLE.CREATE, PermissionConstant.ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<StructureRS> createRole(@Valid @RequestBody RoleRQ roleRQ){
        return response(roleServices.create(roleRQ));
    }

    @Secured({PermissionConstant.ROLE.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> getRole(@PathVariable("id") String id){
        return response(roleServices.getRoleById(Long.parseLong(id)));
    }

    @Secured({PermissionConstant.ROLE.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping
    public ResponseEntity<StructureRS> getAllRoles(BaseListingRQ baseListingRQ){
        return response(roleServices.getAll(baseListingRQ));
    }

    @Secured({PermissionConstant.ROLE.EDIT, PermissionConstant.ROLE_ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<StructureRS> updateRole(Long id,@RequestBody RoleRequestUpdate roleRequestUpdate){
        return response(roleServices.update(id,roleRequestUpdate));
    }

    @Secured({PermissionConstant.ROLE.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<StructureRS> delete(@PathVariable("id") String id){
        return response(roleServices.delete(Long.parseLong(id)));
    }

    @Secured({PermissionConstant.ROLE.SET, PermissionConstant.ROLE_ADMIN})
    @PutMapping("/assignPermission")
    public ResponseEntity<StructureRS> assignPermission(@RequestBody SetPermissionRequest setPermissionRequest) {
        return response(roleServices.setPermission(setPermissionRequest));
    }

    @Secured({PermissionConstant.ROLE.VIEW_PERMISSION, PermissionConstant.ROLE_ADMIN})
    @GetMapping("/listPermissions")
    public ResponseEntity<StructureRS> listAllPermissions(BaseListingRQ baseListingRQ,@RequestParam(value = "roleId",required = false) String roleId,@RequestParam(value="module",required = false) String module){
    
        return response(roleServices.listAllPermissions(baseListingRQ,roleId!=null?Long.parseLong(roleId):null,module));
    }
}
