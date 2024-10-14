package com.kiloit.onlyadmin.model.post.request;

import lombok.Data;

import java.time.Instant;

@Data
public class PostCreateRequest {
    private String title;
    private String description;
    private boolean status;
    private Instant publicAt;

    private Long mediaId;
    private Long user_id;
    private Long topic_id;
}
