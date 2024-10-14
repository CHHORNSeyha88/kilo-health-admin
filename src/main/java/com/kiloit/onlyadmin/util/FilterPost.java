package com.kiloit.onlyadmin.util;

import com.kiloit.onlyadmin.base.BaseListingRQ;
import lombok.Setter;

@Setter
public class FilterPost extends BaseListingRQ {
    private Boolean status;
    private Long userId;
    private Long categoryId;
    private Long topicId;

    public Boolean getStatus() {
        if (status == null)
            return null;
        return status;
    }

    public Long getUserId(){
        if(userId == null)
            return null;
        return userId;
    }

    public Long getCategoryId(){
        if(categoryId == null)
            return  null;
        return categoryId;
    }

    public Long getTopicId(){
        if(topicId == null)
            return null;
        return topicId;
    }
}
