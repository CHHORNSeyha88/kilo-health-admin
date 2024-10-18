package com.kiloit.onlyadmin.model.Category.respone;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.model.post.response.PostListResponse;
import com.kiloit.onlyadmin.model.topic.response.TopicResponseList;
import com.kiloit.onlyadmin.model.user.respone.CategoryRS_user;
import lombok.Data;
import java.util.List;

@Data
public class CategoryRS {

    private Integer id;
    private String name;
    private FileMedia fileMedia;
    private CategoryRS_user user;
    private List<TopicResponseList> topicResponseLists;
    private List<PostListResponse> postResponseLists;

}
