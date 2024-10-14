package com.kiloit.onlyadmin.model.topic.response;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import lombok.Data;

@Data
public class TopicRSById {
    private Integer id;
    private String name;
    // private CategoryEntity category;
    private String description;
    private boolean status;
    private CustomTopicUser userEntity;
}
