package com.kiloit.onlyadmin.model.post.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostCreateRequest {
    @NotNull(message = MessageConstant.POST.POST_TITLE_IS_NULL)
    private String title;
    @NotNull(message = MessageConstant.POST.POST_DESCRIPTION_IS_NULL)
    private String description;
    @NotNull(message = MessageConstant.POST.POST_PUBLIC_AT_IS_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publicAt;
    @NotNull(message = MessageConstant.POST.POST_STATUS_IS_NULL)
    private Boolean status;
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_ID_IS_NULL)
    private Long mediaId;
    @NotNull(message = MessageConstant.TOPIC.TOPIC_ID_IS_NULL)
    private Long topic_id;
}
