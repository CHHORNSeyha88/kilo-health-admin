package com.kiloit.onlyadmin.model.Category.respone;

import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRS {
    private String name;
    private String thumbnail;
    private List<TopicEntity> topicEntities;
    private List<PostEntity> postEntities;
}
