package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.role.request.RoleRQ;
import com.kiloit.onlyadmin.service.RoleServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleServices roleServices;

    @PostMapping
    public ResponseEntity<StructureRS> createRole(@Valid @RequestBody RoleRQ roleRQ){
        return response(roleServices.create(roleRQ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> getRole(@PathVariable("id") String id){
        return response(roleServices.getRoleById(Long.parseLong(id)));
    }

    @GetMapping
    public ResponseEntity<StructureRS> getAllRoles(BaseListingRQ baseListingRQ){
        return response(roleServices.getAll(baseListingRQ));
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<StructureRS> delete(@PathVariable("id") String id){
        return response(roleServices.delete(Long.parseLong(id)));
    }

    @PostMapping("/assignPermission")
    public ResponseEntity<StructureRS> assignPermissionToRole(@RequestParam String roleName, @RequestParam String permissionName) {
        return response(roleServices.assignPermissionToRole(roleName, permissionName));
    }

    @DeleteMapping("/removePermission")
    public ResponseEntity<StructureRS> removePermissionFromRole(@RequestParam String roleName, @RequestParam String permissionName){
        return response(roleServices.removePermissionFromRole(roleName,permissionName));
    }

    @PutMapping("/updatePermission")
    public ResponseEntity<StructureRS> updatePermissionFromRole(@RequestParam String roleName, @RequestBody List<String> permissionNames){
        return response(roleServices.updatePermissionsForRole(roleName,permissionNames));
    }
}
