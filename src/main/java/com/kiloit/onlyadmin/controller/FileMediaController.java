package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.filemedia.request.FileMediaRequest;
import com.kiloit.onlyadmin.service.FileMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class FileMediaController extends BaseController {
    private final FileMediaService fileMediaService;

    @PostMapping
    public ResponseEntity<StructureRS> create(@RequestBody FileMediaRequest request){
        return response(fileMediaService.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<StructureRS> getDetail(@PathVariable Long id){
        return response(fileMediaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<StructureRS> getAll(BaseListingRQ request){
        return response(fileMediaService.findAll(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StructureRS> delete(@PathVariable Long id){
        return response(fileMediaService.delete(id));
    }
}
