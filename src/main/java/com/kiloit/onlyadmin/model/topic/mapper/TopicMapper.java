package com.kiloit.onlyadmin.model.topic.mapper;

import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TopicMapper {
    TopicEntity to(TopicRQ topicRQ);
    
    @Mapping(target = "userEntity",source = "user")
    TopicRSById to(TopicEntity topicEntity);
}
