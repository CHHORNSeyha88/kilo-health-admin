package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.TopicRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.model.topic.mapper.TopicMapper;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService extends BaseService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserRepository userRepository;



    public StructureRS createTopic (TopicRQ topicRQ){
        TopicEntity topicEntity = topicMapper.to(topicRQ);
        Optional<UserEntity> user = userRepository.findById(topicRQ.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User ID not found.");
        }
        topicEntity.setUser(user.get());
        topicEntity = topicRepository.save(topicEntity);
        return response(topicMapper.to(topicEntity));

    }


    public StructureRS getAllTopics() {
        List<TopicEntity> allTopics = topicRepository.findAll();
        List<TopicRSById> topicRSByIdList = allTopics.stream().map(topicMapper::to).toList();
        return response(topicRSByIdList);
    }
    public StructureRS getById(Long id) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isEmpty()) {
            throw new IllegalArgumentException("Topic ID not found.");
        }
        TopicEntity topic = topicEntity.get();
        return response(topicMapper.to(topic));
    }

    public StructureRS DeleteById(Long id) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isEmpty()) {
            throw new IllegalArgumentException("Topic ID not found.");
        }
        topicRepository.delete(topicEntity.get());
        return response("DeleteById"+topicEntity.get());
    }

    public StructureRS updateTopicById(Long id, TopicRQ topicRQ) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isEmpty()) {
            throw new IllegalArgumentException("Topic ID not found.");
        }
        TopicEntity OldEntity = topicEntity.get();
        TopicEntity NewEntity = topicMapper.to(topicRQ);
        BeanUtils.copyProperties(NewEntity,OldEntity, "id", "created_at", "updated_at","categoryId", "userId");
        OldEntity = topicRepository.save(OldEntity);
        return  response(topicMapper.to(OldEntity));
    }

    public String deleteTopicByIdNotNull(Long id){
        Optional<TopicEntity> topicEntity = topicRepository.findByIdAndDeletedAtNull(id);
        if (topicEntity.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);
        topicEntity.get().setDeletedAt(Instant.now());
        topicRepository.save(topicEntity.get());
        return "topic has been deleted with id " + id;
    }
}
