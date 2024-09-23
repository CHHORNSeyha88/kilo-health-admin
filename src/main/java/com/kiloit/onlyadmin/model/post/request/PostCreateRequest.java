package com.kiloit.onlyadmin.model.post.request;

import lombok.Data;

@Data
public class PostCreateRequest {
    private String title;
    private String description;
    private String thumbnail;

    private Long user_id;
    private Long topic_id;
}
