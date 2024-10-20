package com.kiloit.onlyadmin.model.post.request;

import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostUpdateRequest {
    @NotNull(message = MessageConstant.POST.POST_TITLE_IS_NULL)
    private String title;
    @NotNull(message = MessageConstant.POST.POST_DESCRIPTION_IS_NULL)
    private String description;
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_ID_IS_NULL)
    private Long mediaId;
    @NotNull(message = MessageConstant.POST.POST_STATUS_IS_NULL)
    private Boolean status;
}
