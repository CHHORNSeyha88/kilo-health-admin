package com.kiloit.onlyadmin.model.Category.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface CategoryMapper{
    
    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "user.id", source = "userId"),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "fileMediaId",ignore = true),
        @Mapping(target="postEntities",ignore = true),
        @Mapping(target = "topicList",ignore = true)
    })
    CategoryEntity toEntity(CategoryRQ request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "mediaId", source = "fileMediaId.id")
    CategoryRS_List toResponse(CategoryEntity categoryEntity);

    @Mapping(target = "fileMedia",source = "fileMediaId")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "topicEntities",ignore = true)
    CategoryRS from(CategoryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "user", ignore=true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "fileMediaId",ignore = true),
        @Mapping(target="postEntities",ignore = true),
        @Mapping(target = "topicList",ignore = true)
    })
    void fromUpdate(CategoryRQ_Update categoryRQ, @MappingTarget CategoryEntity category);

}
