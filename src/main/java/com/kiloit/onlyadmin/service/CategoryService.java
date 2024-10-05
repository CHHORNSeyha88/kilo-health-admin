package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.Category.mapper.CategoryMapper;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public StructureRS create(CategoryRQ request){
        CategoryEntity categoryEntity = categoryMapper.toEntity(request);
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User id not found"));

        categoryEntity.setUser(userEntity);
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
