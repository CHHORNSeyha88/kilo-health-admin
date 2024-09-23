package com.kiloit.onlyadmin.model.post.response;

import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import lombok.Data;

@Data
public class PostDetailResponse {
    private String title;
    private String description;
    private String thumbnail;

    private UserEntity userEntity;
    private TopicEntity topicEntity;
}
