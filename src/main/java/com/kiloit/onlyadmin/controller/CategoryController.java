package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @Secured({PermissionConstant.CATEGORY.CREATE,PermissionConstant.ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<StructureRS> create(@Valid @RequestBody CategoryRQ request, JwtAuthenticationToken jwt){
        return response(categoryService.create(request));
    }

    @Secured({PermissionConstant.CATEGORY.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping()
    public ResponseEntity<StructureRS> getLists(BaseListingRQ request, JwtAuthenticationToken jwt){
        return response(categoryService.getList(request,jwt));
    }

    @Secured({PermissionConstant.CATEGORY.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping("{id}")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id, JwtAuthenticationToken jwt){
        return response(categoryService.getDetail(id,jwt));
    }

    @Secured({PermissionConstant.CATEGORY.EDIT, PermissionConstant.ROLE_ADMIN})
    @PutMapping("{id}")
    public ResponseEntity<StructureRS> update(@PathVariable Long id,@Valid @RequestBody CategoryRQ_Update request,JwtAuthenticationToken jwt){
        return response(categoryService.updateById(id, request,jwt));
    }

    @Secured({PermissionConstant.CATEGORY.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> deleteUser(@PathVariable("id") Long id, JwtAuthenticationToken jwt) {
        return response(categoryService.deleteById(id,jwt));
    }
}
