package com.kiloit.onlyadmin.model.topic.request;
import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopicRQ {
    @NotNull(message = MessageConstant.CATEGORY.NAME_IS_NULL)
    private String name;
    @NotNull(message = MessageConstant.CATEGORY.CATEGORY_ID_IS_NULL)
    private Long categoryId;
    @NotNull(message = MessageConstant.CATEGORY.USER_ID_IS_NULL)
    private Long userId;
    @NotNull(message = MessageConstant.CATEGORY.MEDIA_ID_IS_NULL)
    private Long fileMediaId;
}
