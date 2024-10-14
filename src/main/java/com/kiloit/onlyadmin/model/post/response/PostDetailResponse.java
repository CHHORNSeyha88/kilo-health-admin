package com.kiloit.onlyadmin.model.post.response;

import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.model.topic.response.TopicResponse;
import com.kiloit.onlyadmin.model.user.respone.UserListResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class PostDetailResponse {
    private String title;
    private String description;
    private boolean status;
    private Instant publicAt;

    private UserListResponse userListResponse;
    private TopicResponse topicRSById;
    private CategoryRS_List categoryRS;
}
