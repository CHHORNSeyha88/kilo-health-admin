package com.kiloit.onlyadmin.model.category.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ;
import com.kiloit.onlyadmin.model.category.request.CategoryRQ_Update;
import com.kiloit.onlyadmin.model.category.respone.CategoryRS;
import com.kiloit.onlyadmin.model.category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.model.post.response.PostListResponse;
import com.kiloit.onlyadmin.model.topic.response.TopicResponseList;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "fileMediaId", ignore = true),
            @Mapping(target = "postEntities", ignore = true),
            @Mapping(target = "topicList", ignore = true)
    })
    CategoryEntity toEntity(CategoryRQ request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "mediaId", source = "fileMediaId")
    CategoryRS_List toResponse(CategoryEntity categoryEntity);

    @Mapping(target = "fileMedia", source = "fileMediaId")
    @Mapping(target = "user", source = "user")
    CategoryRS from(CategoryEntity entity);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "userName", source = "user.username")
    TopicResponseList toTopics(TopicEntity topic);

    @Mapping(target = "categoryName", source = "categoryEntity.name")
    @Mapping(target = "userName", source = "userEntity.username")
    @Mapping(target = "topicName", source = "topicEntity.name")
    PostListResponse toPosts(PostEntity post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "fileMediaId", ignore = true),
            @Mapping(target = "postEntities", ignore = true),
            @Mapping(target = "topicList", ignore = true)
    })
    void fromUpdate(CategoryRQ_Update categoryRQ, @MappingTarget CategoryEntity category);
}
