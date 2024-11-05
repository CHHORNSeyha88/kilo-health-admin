package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
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

    @Secured({"SCOPE_Upload_File_Media", "ROLE_Administrator"})
    @PostMapping("/upload")
    public ResponseEntity<StructureRS> upload(@Valid @ModelAttribute FileUploadForm form, BindingResult result) {
        return response(fileMediaService.upload(form.files()));
    }

    @Secured({"SCOPE_View_File_Media", "ROLE_Administrator"})
    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> getDetail(@PathVariable Long id){
        return response(fileMediaService.findById(id));
    }

    @Secured({"SCOPE_View_File_Media", "ROLE_Administrator"})
    @GetMapping
    public ResponseEntity<StructureRS> getAll(BaseListingRQ request){
        return response(fileMediaService.findAll(request));
    }

    @Secured({"SCOPE_Delete_File_Media", "ROLE_Administrator"})
    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> delete(@PathVariable Long id){
        return response(fileMediaService.delete(id));
    }

}
