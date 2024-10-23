package com.kiloit.onlyadmin.model.post.response;

import com.kiloit.onlyadmin.model.category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.model.topic.response.TopicResponseList;
import com.kiloit.onlyadmin.model.user.respone.UserListResponse;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDetailResponse {
    private Long id;
    private String title;
    private String description;
    private boolean status;
    private Integer time_read;
    private LocalDateTime publicAt;

    private UserListResponse user;
    private TopicResponseList topic;
    private CategoryRS_List category;
}
