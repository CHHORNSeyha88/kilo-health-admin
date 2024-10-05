package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.service.CategoryService;
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

    @GetMapping("{id}")
    public ResponseEntity<StructureRS> getList(@PathVariable Long id){
        return response(categoryService.getListById(id));
    }





}
