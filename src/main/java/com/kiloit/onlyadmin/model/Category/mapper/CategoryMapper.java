package com.kiloit.onlyadmin.model.Category.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CategoryMapper{


    CategoryEntity toEntity(CategoryRQ request);
    CategoryRS_List toResponse(CategoryEntity categoryEntity);

    
    CategoryEntity toUpdateEntity(CategoryRQ_Update request);
    @Mapping(target = "userId", source = "user.id")
    CategoryRS_List toResponseUpdate(CategoryEntity category);

}
