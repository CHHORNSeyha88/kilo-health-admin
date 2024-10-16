package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.service.TopicService;
import com.kiloit.onlyadmin.util.FilterTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/topics")
@RequiredArgsConstructor
public class TopicController extends BaseController {

    private final TopicService topicService;

   @PostMapping
   public ResponseEntity<StructureRS> createTopic(@RequestBody TopicRQ topicRQ){
       return response(topicService.createTopic(topicRQ));
   }

   @GetMapping("/{id}")
   public ResponseEntity<StructureRS> getTopicById(@PathVariable Long id){
       return response(topicService.getById(id));
   }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<StructureRS> DeleteTopicById(@PathVariable Long id){
        return response(topicService.DeleteById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<StructureRS> updateTopicById(@PathVariable Long id, @RequestBody TopicRQ topicRQ){
        return response(topicService.updateTopicById(id, topicRQ));
    }

    @DeleteMapping("soft-delete/{id}")
    public ResponseEntity<StructureRS> DeleteNotNullTopicById(@PathVariable Long id){
       return response(topicService.deleteTopicByIdNotNull(id));
    }
    @GetMapping()
    public ResponseEntity<StructureRS>getList(FilterTopic filterTopic){
        return response(topicService.getTopicList(filterTopic));
    }

}
