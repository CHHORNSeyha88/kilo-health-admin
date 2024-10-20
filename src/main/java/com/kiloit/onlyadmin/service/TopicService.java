package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.TopicRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.topic.mapper.TopicMapper;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.response.TopicRSById;
import com.kiloit.onlyadmin.util.FilterTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.kiloit.onlyadmin.database.specification.TopicSpecification.filter;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService extends BaseService {
    private final CategoryRepository categoryRepository;

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserRepository userRepository;

    public StructureRS createTopic (TopicRQ topicRQ){
        TopicEntity topicEntity = topicMapper.to(topicRQ);
        Optional<UserEntity> user = userRepository.findById(topicRQ.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User ID not found.");
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByID(topicRQ.getCategoryId());
        if(categoryEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        topicEntity.setCategory(categoryEntity.get());
        topicEntity.setUser(user.get());
        topicEntity = topicRepository.save(topicEntity);

        TopicRSById topic = topicMapper.to(topicEntity);
        topic.setUserEntity(topicMapper.to(topicEntity.getUser()));
        topic.setCategory(topicMapper.to(topicEntity.getCategory()));
        return response(topic);
    }
    public StructureRS getById(Long id) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isEmpty()) {
           throw new NotFoundException("Topic not found.");
        }
        TopicEntity topic = topicEntity.get();
        return response(topicMapper.to(topic));
    }

    public StructureRS updateTopicById(Long id, TopicRQ topicRQ) {
        Optional<TopicEntity> topicEntity = topicRepository.findById(id);
        if (topicEntity.isEmpty()) {
            throw new IllegalArgumentException("Topic ID not found.");
        }
        topicEntity.get().setName(topicRQ.getName());
        return  response(topicMapper.to(topicRepository.save(topicEntity.get())));
    }

    public String deleteTopicByIdNotNull(Long id){
        Optional<TopicEntity> topicEntity = topicRepository.findByIdAndDeletedAtNull(id);
        if (topicEntity.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);
        // topicEntity.get().setDeletedAt();
        topicRepository.save(topicEntity.get());
        return "topic has been deleted with id " + id;
    }
    @Transactional(readOnly = true)
    public StructureRS getTopicList(FilterTopic filterTopic){
        Page<TopicEntity> topicList = topicRepository.findAll(filter(filterTopic.getQuery(),filterTopic.getUserId(),filterTopic.getCategoryId()),filterTopic.getPageable());
        return response(topicList.stream().map(topicMapper::to),topicList);
    }


}
