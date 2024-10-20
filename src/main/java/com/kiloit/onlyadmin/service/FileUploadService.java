package com.kiloit.onlyadmin.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.service.fileUpload.dto.FileUploadResponse;

@Service
public class FileUploadService extends BaseService{

    @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;

    public StructureRS upload(MultipartFile fileUpload) {
       return response(FileUpload(fileUpload));
    }

    public FileUploadResponse FileUpload(MultipartFile fileUpload){
        String newName = UUID.randomUUID().toString();
        @SuppressWarnings("null")
        String extension = fileUpload.getContentType().split("/")[1];

        Path path = Paths.get(serverPath+String.format("%s.%s",newName,extension));
        try {
            Files.copy(fileUpload.getInputStream(),path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File upload has not been found...");
        }
        return FileUploadResponse.builder()
                                 .name(String.format("%s.%s",newName,extension))
                                 .uri(baseUri+String.format("%s.%s",newName,extension))
                                 .contentType(fileUpload.getContentType())
                                 .size(fileUpload.getSize())
                                 .build();
    }

    public StructureRS uploadMulitipartFile(List<MultipartFile> multipartFile) {
        List<FileUploadResponse> fileUploadResponses = new ArrayList<>();

        multipartFile.forEach(file->{
            FileUploadResponse fileUploadResponse = FileUpload(file);
            fileUploadResponses.add(fileUploadResponse);
        });
        return response(fileUploadResponses);
        
    }

    public StructureRS deleteFile(String fileName) {
        Path path = Paths.get(serverPath+fileName);
        if(Files.exists(path)){
            try{
                Files.delete(path);
            }catch(IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File is failed to delete");
            }
        }
        return response("File has been deleted successfully");
    }
    
}
