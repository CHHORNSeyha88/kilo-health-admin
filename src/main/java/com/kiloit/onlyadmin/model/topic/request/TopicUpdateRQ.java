package com.kiloit.onlyadmin.model.topic.request;

import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopicUpdateRQ {
    @NotNull(message = MessageConstant.CATEGORY.NAME_IS_NULL)
    private String name;
}
