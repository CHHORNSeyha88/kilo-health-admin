package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.request.TopicUpdateRQ;
import com.kiloit.onlyadmin.service.TopicService;
import com.kiloit.onlyadmin.service.Transactional;
import com.kiloit.onlyadmin.util.FilterTopic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/topics")
@RequiredArgsConstructor
public class TopicController extends BaseController {

    private final TopicService topicService;

    @Secured({"SCOPE_Create_Topic", "ROLE_Administrator"})
   @PostMapping
   public ResponseEntity<StructureRS> createTopic(@Valid @RequestBody TopicRQ topicRQ){
       return response(topicService.createTopic(topicRQ));
   }

    @Secured({"SCOPE_View_Topic", "ROLE_Administrator"})
   @GetMapping("/{id}")
   public ResponseEntity<StructureRS> getTopicById(@PathVariable Long id){
       return response(topicService.getById(id));
   }

    @Secured({"SCOPE_Delete_Topic", "ROLE_Administrator"})
    @DeleteMapping("delete/{id}")
    public ResponseEntity<StructureRS> DeleteTopicById(@PathVariable Long id){
        return response(topicService.deleteTopicByIdNotNull(id));
    }

    @Secured({"SCOPE_Edit_Topic", "ROLE_Administrator"})
    @PutMapping("update/{id}")
    public ResponseEntity<StructureRS> updateTopicById(@Valid @PathVariable Long id, @RequestBody TopicUpdateRQ topicRQ){
        return response(topicService.updateTopicById(id, topicRQ));
    }

    @Secured({"SCOPE_View_Topic", "ROLE_Administrator"})
    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<StructureRS>getList(FilterTopic filterTopic){
        return response(topicService.getTopicList(filterTopic));
    }

}
