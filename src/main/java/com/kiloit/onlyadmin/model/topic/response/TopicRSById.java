package com.kiloit.onlyadmin.model.topic.response;
import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;
import lombok.Data;

@Data
public class TopicRSById {
    private Long id;
    private String name;
    private CustomTopicCategory category;
    private CustomTopicUser user;
    private FileMediaResponse fileMedia;
}
