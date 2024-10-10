package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.service.CategoryService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<StructureRS> create(@RequestBody CategoryRQ request){
        return response(categoryService.create(request));
    }

    @GetMapping()
    public ResponseEntity<StructureRS> getLists(BaseListingRQ request){
        return response(categoryService.getList(request));
    }
    @GetMapping("{id}")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id){
        return response(categoryService.getDetail(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<StructureRS> update(@PathVariable Long id, @RequestBody CategoryRQ_Update request){
        return response(categoryService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> deleteUser(@PathVariable("id") String id) {
        return response(categoryService.deleteById(Long.parseLong(id)));
    }
}
