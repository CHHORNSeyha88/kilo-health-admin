package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.Category.mapper.CategoryMapper;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public StructureRS create(CategoryRQ request){
        CategoryEntity categoryEntity = categoryMapper.toEntity(request);
        UserEntity userEntity = userRepository
                .findById(request.getUserId())
                .orElseThrow(()-> new BadRequestException("User not found..."));
        categoryEntity.setUser(userEntity);
        categoryEntity = categoryRepository.save(categoryEntity);
        return response(categoryMapper.toResponse(categoryEntity));

    }
    public StructureRS getList(){
        List<CategoryEntity> categoryEntity = categoryRepository.findAll();
        return response(categoryEntity.stream().map(categoryMapper::toResponse).toList());
    }

    public StructureRS getDetail(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByID(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException("Category not found...");
        }
        return response(categoryMapper.from(categoryEntity.get()));
    }

    public StructureRS updateById(Long id, CategoryRQ_Update request){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException("Category id not found");
        }
        CategoryEntity categoryOld = categoryEntity.get();
        categoryMapper.fromUpdate(request, categoryOld);
        categoryOld = categoryRepository.save(categoryOld);
        return response(categoryMapper.toResponse(categoryOld));
    }
    public StructureRS deleteById(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException("Category id not found");
        }
        categoryEntity.get().setDeletedAt(Instant.now());
        categoryRepository.save(categoryEntity.get());
        return response(HttpStatus.NO_CONTENT);
    }
}
