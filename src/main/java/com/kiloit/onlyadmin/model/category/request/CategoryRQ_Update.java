package com.kiloit.onlyadmin.model.category.request;

import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRQ_Update {

    @NotNull(message = MessageConstant.CATEGORY.NAME_IS_NULL)
    private String name;

}
