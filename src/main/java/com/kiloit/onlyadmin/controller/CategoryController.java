package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
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

    @Secured({"SCOPE_Create_Category", "SCOPE_Administrator"})
    @PostMapping
    public ResponseEntity<StructureRS> create(@Valid @RequestBody CategoryRQ request, JwtAuthenticationToken jwt){
        return response(categoryService.create(request));
    }

    @Secured({"SCOPE_View_Category", "ROLE_Administrator"})
    @GetMapping()
    public ResponseEntity<StructureRS> getLists(BaseListingRQ request){
        return response(categoryService.getList(request));
    }

    @Secured({"SCOPE_View_Category", "ROLE_Administrator"})
    @GetMapping("{id}")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id){
        return response(categoryService.getDetail(id));
    }

    @Secured({"SCOPE_Update_Category", "ROLE_Administrator"})
    @PutMapping("{id}")
    public ResponseEntity<StructureRS> update(@PathVariable Long id,@Valid @RequestBody CategoryRQ_Update request){
        return response(categoryService.updateById(id, request));
    }

    @Secured({"SCOPE_Delete_Category", "ROLE_Administrator"})
    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> deleteUser(@PathVariable("id") String id) {
        return response(categoryService.deleteById(Long.parseLong(id)));
    }
}
