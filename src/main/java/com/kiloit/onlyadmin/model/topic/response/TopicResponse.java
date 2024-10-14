package com.kiloit.onlyadmin.model.topic.response;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import lombok.Data;

@Data
public class TopicResponse {
    private Integer id;
    private String name;
    private boolean status;
}
