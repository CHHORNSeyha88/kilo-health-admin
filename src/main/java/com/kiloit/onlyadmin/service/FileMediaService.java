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
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileMediaService extends BaseService {
    private final FileMediaRepository fileMediaRepository;
    private final FileMediaMapper fileMediaMapper;
    @Value("${file-upload.server-path}")
    private String serverPath;
    @Value("${file-upload.base-uri}")
    private String baseUri;

    public FileMediaResponse FileUpload(MultipartFile fileUpload){
        @SuppressWarnings("null")
        String extension = fileUpload.getContentType().split("/")[1];
        String newName = UUID.randomUUID().toString();
        try {
            Files.copy(fileUpload.getInputStream(),Paths.get(serverPath+newName+"."+extension));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
        }
        return FileMediaResponse.builder().fileName(newName+"."+extension).fileUrl(baseUri+newName+"."+extension).fileType(fileUpload.getContentType()).fileSize(fileUpload.getSize()).build();
    }

    @Transactional
    public StructureRS upload(List<MultipartFile> multipartFile) {
        List<FileMediaResponse> fileMediaResponses = new ArrayList<>();
        multipartFile.forEach(file->{
            FileMedia fileMedia = fileMediaMapper.fromFileMediaResponse(FileUpload(file));
            fileMedia.setCreatedAt(Instant.now());
            fileMediaResponses.add(fileMediaMapper.toFileMediaResponse(fileMediaRepository.save(fileMedia)));
        });
        return response(fileMediaResponses);
    }

    public StructureRS deleteFile(String fileName) {
        Path path = Paths.get(serverPath+fileName);
        if(Files.exists(path)){
            try{
                Files.delete(path);
            }catch(IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
            }
        }
        return response(MessageConstant.FILEMEDIA.FILE_MEDIA_HAS_BEEN_DELETE);
    }

    @Transactional(readOnly = true)
    public StructureRS findById(Long id){
        Optional<FileMedia> fileMedia = fileMediaRepository.findById(id);
        if (fileMedia.isEmpty()) throw new NotFoundException(MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
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
