package com.kiloit.onlyadmin.model.category.respone;
import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;
import com.kiloit.onlyadmin.model.user.respone.CategoryRS_user;
import lombok.Data;

@Data
public class CategoryRS {
    private Integer id;
    private String name;
    private FileMediaResponse fileMedia;
    private CategoryRS_user user;
}
