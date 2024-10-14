package com.kiloit.onlyadmin.model.topic.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.CustomTopicCategory;
import com.kiloit.onlyadmin.model.topic.response.CustomTopicUser;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import com.kiloit.onlyadmin.model.topic.response.TopictResponseList;
import com.kiloit.onlyadmin.model.user.respone.UserDetailRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TopicMapper {

    TopicEntity to(TopicRQ topicRQ);
    @Mapping(target = "userEntity", source = "user")
    TopicRSById to(TopicEntity topicEntity);
    CustomTopicCategory to(CategoryEntity categoryEntity);
    CustomTopicUser to(UserEntity userEntity);
    
    @Mapping(target = "categoryName",source = "category.name")
    @Mapping(target = "userName" , source = "user.firstname")
    TopictResponseList toResponse(TopicEntity topicEntity);

}
