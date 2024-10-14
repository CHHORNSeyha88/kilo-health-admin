package com.kiloit.onlyadmin.util;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import lombok.Setter;

@Setter
public class FilterTopic extends BaseListingRQ {
    private Long categoryId;
    private Long userId;

    public Long getCategoryId(){
        if(categoryId==null)
            return null;
        return categoryId;
    }

    public Long getUserId(){
        if(userId == null)
            return null;
        return userId;
    }

}
