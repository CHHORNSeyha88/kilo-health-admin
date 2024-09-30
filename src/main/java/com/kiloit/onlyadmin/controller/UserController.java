package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.request.UserRQ;
import com.kiloit.onlyadmin.model.request.UserUpdateRequest;
import com.kiloit.onlyadmin.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<StructureRS> list(@Valid BaseListingRQ request) {
        return response(userService.list(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> detail(@PathVariable("id") String id) {
        return response(userService.detail(Long.parseLong(id)));
    }

    @PostMapping
    public ResponseEntity<StructureRS> create(@Valid @RequestBody UserRQ request){
        return response(userService.create(request));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<StructureRS> update(@PathVariable("id") String id,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return response(userService.update(Long.parseLong(id),userUpdateRequest));
    }

    @PatchMapping("/{id}/soft-delete")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }

}
