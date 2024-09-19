package com.kiloit.onlyadmin.model.topic.mapper;

import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import org.mapstruct.Mapper;

@Mapper
public interface TopicMapper {

    TopicEntity to(TopicRQ topicRQ);

    TopicRSById to(TopicEntity topicEntity);
}
