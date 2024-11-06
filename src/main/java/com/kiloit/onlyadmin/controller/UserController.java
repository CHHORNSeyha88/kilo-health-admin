package com.kiloit.onlyadmin.controller;
import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.user.request.UserRQ;
import com.kiloit.onlyadmin.model.user.request.UserUpdateRequest;
import com.kiloit.onlyadmin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @Secured({PermissionConstant.USER.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping
    public ResponseEntity<StructureRS> list(@Valid BaseListingRQ request) {
        return response(userService.list(request));
    }

    @Secured({PermissionConstant.USER.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> detail(@PathVariable("id") String id) {
        return response(userService.detail(Long.parseLong(id)));
    }

    @Secured({PermissionConstant.USER.CREATE, PermissionConstant.ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<StructureRS> create(@Valid @RequestBody UserRQ request){
        return response(userService.create(request));
    }

    @Secured({PermissionConstant.USER.EDIT, PermissionConstant.ROLE_ADMIN})
    @PutMapping("/{id}/update")
    public ResponseEntity<StructureRS> update(@PathVariable("id") String id,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return response(userService.update(Long.parseLong(id),userUpdateRequest));
    }

    @Secured({PermissionConstant.USER.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<StructureRS> delete(@PathVariable("id") Long id){
        return response(userService.delete(id));
    }

}
