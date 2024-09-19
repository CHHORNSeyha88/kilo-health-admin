package com.kiloit.onlyadmin.model.Category.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.model.Category.request.CategoryRQ;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper{

    CategoryEntity to(CategoryRQ categoryRQ);


}
