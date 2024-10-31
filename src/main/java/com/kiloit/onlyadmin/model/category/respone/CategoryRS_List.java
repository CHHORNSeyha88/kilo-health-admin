package com.kiloit.onlyadmin.model.category.respone;

import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;
import lombok.Data;

@Data
public class CategoryRS_List {

    private Integer id;
    private String name;
    private Long userId;
    private FileMediaResponse mediaId;

}
