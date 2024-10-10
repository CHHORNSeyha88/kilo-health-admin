package com.kiloit.onlyadmin.model.Category.respone;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.model.user.respone.UserDetailRS;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRS {
    private String name;
    private FileMedia fileMedia;
    private UserDetailRS user;
    private List<TopicEntity> topicEntities;
    private List<PostEntity> postEntities;

}
