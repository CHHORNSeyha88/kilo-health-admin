package com.kiloit.onlyadmin.model.Category.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface CategoryMapper{

    @Mapping(target = "user.id", source = "userId")
    CategoryEntity toEntity(CategoryRQ request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "mediaId", source = "fileMediaId.id")
    CategoryRS_List toResponse(CategoryEntity categoryEntity);

    @Mapping(target = "fileMedia",source = "fileMediaId")
    @Mapping(target = "user", source = "user")
    CategoryRS from(CategoryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(CategoryRQ_Update categoryRQ, @MappingTarget CategoryEntity category);

}
