package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.ListMediaEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.FileMediaRepository;
import com.kiloit.onlyadmin.database.repository.ListMediaRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.Category.mapper.CategoryMapper;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FileMediaRepository fileMediaRepository;
    private final ListMediaRepository listMediaRepository;

    @Transactional
    public StructureRS create(CategoryRQ request){
        CategoryEntity categoryEntity = categoryMapper.toEntity(request);
        UserEntity userEntity = userRepository
                .findById(request.getUserId())
                .orElseThrow(()-> new BadRequestException("User not found..."));
        categoryEntity.setUser(userEntity);
        ListMediaEntity listMedia = new ListMediaEntity();
        listMedia = listMediaRepository.save(listMedia);
        FileMedia fileMedia = new FileMedia();
        fileMedia.setListMedia(listMedia);
        fileMedia.setFileName(request.getFileName());
        fileMedia.setFileType(request.getFileType());
        fileMedia.setFilePath(request.getFilePath());
        fileMedia.setFileSize(request.getFileSize());
        fileMedia.setUploadDate(LocalDateTime.now());
        fileMediaRepository.save(fileMedia);
        categoryEntity.setListMediaId(listMedia.getId());
        categoryEntity = categoryRepository.save(categoryEntity);
        return response(categoryMapper.toResponse(categoryEntity));

    }
    public StructureRS getListById(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException("Category id not found");
        }
        return response(categoryMapper.toResponse(categoryEntity.get()));
    }

//    public StructureRS updateById(Long id, CategoryRQ_Update request){
//    Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
//    if(categoryEntity.isEmpty()){
//        throw new NotFoundException("Category id not found");
//    }
//
//        return response(categoryMapper.toResponseUpdate(categoryEntity));
//    }







}
