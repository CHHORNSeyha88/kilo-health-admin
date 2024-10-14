package com.kiloit.onlyadmin.model.post.request;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private String title;
    private String description;

    private Long mediaId;
}
