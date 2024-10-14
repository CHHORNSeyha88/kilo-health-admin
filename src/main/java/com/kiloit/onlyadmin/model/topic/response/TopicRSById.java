package com.kiloit.onlyadmin.model.topic.response;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import lombok.Data;

@Data
public class TopicRSById {
    private Long id;
    private String name;
    private String description;
    private CustomTopicCategory category;
    private boolean status;
    private CustomTopicUser userEntity;
}
