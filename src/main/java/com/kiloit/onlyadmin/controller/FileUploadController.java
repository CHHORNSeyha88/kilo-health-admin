package com.kiloit.onlyadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.service.FileUploadService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController extends BaseController {
    private final FileUploadService fileUploadService;
    @PostMapping
    public ResponseEntity<StructureRS> upload(@RequestPart("file") MultipartFile file){
        return response(fileUploadService.upload(file));
    }

    @PostMapping("/multipartfile")
    public ResponseEntity<StructureRS> uploadMulipartFile(@RequestPart("files") List<MultipartFile> files){
        return response(fileUploadService.uploadMulitipartFile(files));
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<StructureRS> deleteFile(@PathVariable("fileName") String fileName){
        return response(fileUploadService.deleteFile(fileName));
    }
    
}
