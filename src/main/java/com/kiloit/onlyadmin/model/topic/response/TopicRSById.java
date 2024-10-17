package com.kiloit.onlyadmin.model.topic.response;
import lombok.Data;

@Data
public class TopicRSById {
    private Long id;
    private String name;
    private CustomTopicCategory category;
    private CustomTopicUser userEntity;
//    private String description;
//    private Boolean status;
}
