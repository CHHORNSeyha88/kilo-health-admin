package com.kiloit.onlyadmin.model.topic.request;
import lombok.Data;

@Data
public class TopicRQ {
    private String name;
    private Long categoryId;
    private Long userId;
    private Long fileMediaId;
}
