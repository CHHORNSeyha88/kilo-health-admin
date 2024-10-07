package com.kiloit.onlyadmin.model.topic.request;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import lombok.Data;

@Data
public class TopicRQ {
    private String name;
    private Long categoryId;
    private Long userId;
}
