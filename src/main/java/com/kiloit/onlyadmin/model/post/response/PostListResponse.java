package com.kiloit.onlyadmin.model.post.response;

import lombok.Data;

@Data
public class PostListResponse {
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private String url;
    private boolean status;

    private String topicName;
    private String userName;
    private String categoryName;

}
