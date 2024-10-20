package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.repository.FileMediaRepository;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.filemedia.mapper.FileMediaMapper;
import com.kiloit.onlyadmin.model.filemedia.request.FileMediaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import static com.kiloit.onlyadmin.database.specification.FileMediaSpecification.*;
@Service
@RequiredArgsConstructor
public class FileMediaService extends BaseService {
    private final FileMediaRepository fileMediaRepository;
    private final FileMediaMapper fileMediaMapper;

    @Transactional
    public StructureRS create(FileMediaRequest request){
        return response(fileMediaMapper.toFileMediaResponse(fileMediaRepository.save(fileMediaMapper.toFileMedia(request))));
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
        Page<FileMedia> list = fileMediaRepository.findAll(filter(request.getQuery()),request.getPageable());
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
