package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.filemedia.request.FileUploadForm;
import com.kiloit.onlyadmin.service.FileMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileMediaController extends BaseController {
    private final FileMediaService fileMediaService;

    @Secured({PermissionConstant.FILE.UPLOAD, PermissionConstant.ROLE_ADMIN})
    @PostMapping("/upload")
    public ResponseEntity<StructureRS> upload(@Valid @ModelAttribute FileUploadForm form, BindingResult result) {
        return response(fileMediaService.upload(form.files()));
    }

    @Secured({PermissionConstant.FILE.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> getDetail(@PathVariable Long id){
        return response(fileMediaService.findById(id));
    }

    @Secured({PermissionConstant.FILE.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping
    public ResponseEntity<StructureRS> getAll(BaseListingRQ request){
        return response(fileMediaService.findAll(request));
    }

    @Secured({PermissionConstant.FILE.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> delete(@PathVariable Long id){
        return response(fileMediaService.delete(id));
    }

}
