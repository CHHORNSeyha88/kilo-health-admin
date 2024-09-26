package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<StructureRS> list(BaseListingRQ request) {
        return response(userService.list(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> detail(@PathVariable("id") String id) {
        return response(userService.detail(Long.parseLong(id)));
    }

    @PostMapping
    public ResponseEntity<StructureRS> create(@RequestBody UserRQ request){
        return response(userService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StructureRS> update(@PathVariable("id") String id){
        return null;
    }

    @PatchMapping("/{id}/soft-delete")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }

}
