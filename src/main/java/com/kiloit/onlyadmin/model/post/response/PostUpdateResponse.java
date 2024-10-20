package com.kiloit.onlyadmin.model.post.response;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import lombok.Data;

@Data
public class PostUpdateResponse {
    private String title;
    private String description;
    private FileMedia fileMedia;
}
