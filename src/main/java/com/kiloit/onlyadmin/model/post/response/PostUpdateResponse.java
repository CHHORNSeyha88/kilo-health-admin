package com.kiloit.onlyadmin.model.post.response;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import lombok.Data;

@Data
public class PostUpdateResponse {
    private String title;
    private String description;

    private FileMedia fileMedia;
}
