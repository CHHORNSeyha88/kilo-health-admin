package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.filemedia.request.FileUploadForm;
import com.kiloit.onlyadmin.service.FileMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileMediaController extends BaseController {
    private final FileMediaService fileMediaService;

    @PostMapping("/upload")
    public ResponseEntity<StructureRS> uploadMultipleFiles(@Valid @ModelAttribute FileUploadForm form, BindingResult result) {
        if (result.hasErrors()) {
            return response(result.getAllErrors());
        }

        return response(fileMediaService.uploadMulitipartFile(form.getFiles()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureRS> getDetail(@PathVariable Long id){
        return response(fileMediaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<StructureRS> getAll(BaseListingRQ request){
        return response(fileMediaService.findAll(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StructureRS> delete(@PathVariable Long id){
        return response(fileMediaService.delete(id));
    }
}
