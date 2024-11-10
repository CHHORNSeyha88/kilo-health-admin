package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.request.TopicUpdateRQ;
import com.kiloit.onlyadmin.service.TopicService;
import com.kiloit.onlyadmin.service.Transactional;
import com.kiloit.onlyadmin.util.FilterTopic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/topics")
@RequiredArgsConstructor
public class TopicController extends BaseController {

    private final TopicService topicService;

    @Secured({PermissionConstant.TOPIC.CREATE, PermissionConstant.ROLE_ADMIN})
   @PostMapping
   public ResponseEntity<StructureRS> createTopic(@Valid @RequestBody TopicRQ topicRQ){
       return response(topicService.createTopic(topicRQ));
   }

    @Secured({PermissionConstant.TOPIC.VIEW, PermissionConstant.ROLE_ADMIN})
   @GetMapping("/{id}")
   public ResponseEntity<StructureRS> getTopicById(@PathVariable Long id, JwtAuthenticationToken jwt){
       return response(topicService.getById(id,jwt));
   }

    @Secured({PermissionConstant.TOPIC.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("delete/{id}")
    public ResponseEntity<StructureRS> DeleteTopicById(@PathVariable Long id, JwtAuthenticationToken jwt){
        return response(topicService.deleteTopicByIdNotNull(id,jwt));
    }

    @Secured({PermissionConstant.TOPIC.EDIT, PermissionConstant.ROLE_ADMIN})
    @PutMapping("update/{id}")
    public ResponseEntity<StructureRS> updateTopicById(@Valid @PathVariable Long id, @RequestBody TopicUpdateRQ topicRQ,JwtAuthenticationToken jwt){
        return response(topicService.updateTopicById(id, topicRQ,jwt));
    }

    @Secured({PermissionConstant.TOPIC.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping()
    public ResponseEntity<StructureRS>getList(FilterTopic filterTopic,JwtAuthenticationToken jwt){
        return response(topicService.getTopicList(filterTopic,jwt));
    }

}
