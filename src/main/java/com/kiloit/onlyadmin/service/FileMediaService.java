package com.kiloit.onlyadmin.service;
import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.repository.FileMediaRepository;
import com.kiloit.onlyadmin.database.specification.FileMediaSpecification;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.filemedia.mapper.FileMediaMapper;
import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMediaService extends BaseService {
    private final FileMediaRepository fileMediaRepository;
    private final FileMediaMapper fileMediaMapper;

   @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;

    public FileMediaResponse FileUpload(MultipartFile fileUpload){
        String newName = UUID.randomUUID().toString();

        @SuppressWarnings("null")
        String extension = fileUpload.getContentType().split("/")[1];
        Path path = Paths.get(serverPath+String.format("%s.%s",newName,extension));

        try {
            Files.copy(fileUpload.getInputStream(),path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File upload has not been found...");
        }
        
        return FileMediaResponse.builder()
                                 .fileName(String.format("%s.%s",newName,extension))
                                 .fileUrl(baseUri+String.format("%s.%s",newName,extension))
                                 .fileType(fileUpload.getContentType())
                                 .fileSize(fileUpload.getSize())
                                 .build();
    }

    public StructureRS uploadMulitipartFile(List<MultipartFile> multipartFile) {

        multipartFile.forEach(file->{

            FileMedia fileMedia = new FileMedia();
            FileMediaResponse fileUploadResponse = FileUpload(file);

            fileMedia = fileMediaMapper.fromFileMediaResponse(fileUploadResponse);
            fileMedia.setCreatedAt(Instant.now());
            fileMedia.setModifiedAt(Instant.now()); 

            fileMediaRepository.save(fileMedia);
            
        });
        return response(MessageConstant.FILEMEDIA.FILE_MEDIA_HAS_BEEN_CREATED);
        
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

    @Transactional(readOnly = true)
    public StructureRS findById(Long id){
        Optional<FileMedia> fileMedia = fileMediaRepository.findById(id);
        if (fileMedia.isEmpty()){
            throw new NotFoundException(MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
        }
        return response(fileMediaMapper.toFileMediaResponse(fileMedia.get()));
    }

    @Transactional(readOnly = true)
    public StructureRS findAll(BaseListingRQ request){
        Page<FileMedia> list = fileMediaRepository.findAll(FileMediaSpecification.filter(request.getQuery()),request.getPageable());
        return response(list.getContent().stream().map(fileMediaMapper::toFileMediaResponse),list);
    }

    @Transactional
    public StructureRS delete(Long id){
        Optional<FileMedia> fileMedia = fileMediaRepository.findByIdAndDeletedAtIsNull(id);
        if (fileMedia.isEmpty()){
            throw new NotFoundException(MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
        }
        fileMedia.get().setDeletedAt(Instant.now());
        fileMediaRepository.save(fileMedia.get());
        return response(HttpStatus.ACCEPTED,MessageConstant.FILEMEDIA.FILE_MEDIA_HAS_BEEN_DELETE);
    }
}
