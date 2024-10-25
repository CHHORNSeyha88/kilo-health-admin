package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.FileMediaRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.category.mapper.CategoryMapper;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ_Update;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import static com.kiloit.onlyadmin.database.specification.CategorySpecification.*;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FileMediaRepository fileMediaRepository;

    @Transactional
    public StructureRS create(CategoryRQ request){
        Optional<UserEntity> user = userRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new NotFoundException(MessageConstant.USER.USER_NOT_FOUND);
        }
        Optional<FileMedia> fileMedia = fileMediaRepository.findByIdAndDeletedAtIsNull(request.getMediaId());
        if(fileMedia.isEmpty()){
            throw new NotFoundException(MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
        }
        CategoryEntity categoryEntity = categoryMapper.toEntity(request);
        categoryEntity.setUser(user.get());
        categoryEntity.setFileMediaId(fileMedia.get());
        return response(categoryMapper.from(categoryRepository.save(categoryEntity)));
    }

    @Transactional(readOnly = true)
    public StructureRS getList(BaseListingRQ request){
        Page<CategoryEntity> listCategory = categoryRepository.findAll(filter(request.getQuery()),request.getPageable());
        return response(listCategory.stream().map(categoryMapper::toResponse),listCategory);

    }
    @Transactional(readOnly = true)
    public StructureRS getDetail(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByID(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        return response(categoryMapper.from(categoryEntity.get()));
    }

    @Transactional
    public StructureRS updateById(Long id, CategoryRQ_Update request){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByID(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        CategoryEntity categoryOld = categoryEntity.get();
        categoryMapper.fromUpdate(request, categoryOld);
        categoryOld = categoryRepository.save(categoryOld);
        return response(categoryMapper.toResponse(categoryOld));
    }

    @Transactional
    public StructureRS deleteById(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        categoryEntity.get().setDeletedAt(Instant.now());
        categoryRepository.save(categoryEntity.get());
        return response(HttpStatus.ACCEPTED,MessageConstant.CATEGORY.CATEGORY_HAS_BEEN_DELETED);
    }
}
