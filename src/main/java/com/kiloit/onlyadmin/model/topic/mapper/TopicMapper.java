package com.kiloit.onlyadmin.model.topic.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.CustomTopicCategory;
import com.kiloit.onlyadmin.model.topic.response.CustomTopicUser;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import com.kiloit.onlyadmin.model.topic.response.TopicResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface TopicMapper {

      @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target = "fileMediaId",ignore = true),
        @Mapping(target = "category",ignore = true),
        @Mapping(target="user",ignore = true),
        @Mapping(target = "postEntities",ignore = true)
    })
    TopicEntity to(TopicRQ topicRQ);

    @Mapping(target = "userEntity", source = "user")
    @Mapping(target = "description",ignore = true)
    @Mapping(target = "status",ignore=true)
    TopicRSById to(TopicEntity topicEntity);

    CustomTopicCategory to(CategoryEntity categoryEntity);
    CustomTopicUser to(UserEntity userEntity);
    
    @Mapping(target = "categoryName",source = "category.name")
    @Mapping(target = "userName" , source = "user.firstname")
    TopicResponseList toResponse(TopicEntity topicEntity);

}
