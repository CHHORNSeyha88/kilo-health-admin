package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import com.kiloit.onlyadmin.database.repository.CategoryRepository;
import com.kiloit.onlyadmin.database.repository.FileMediaRepository;
import com.kiloit.onlyadmin.database.repository.TopicRepository;
import com.kiloit.onlyadmin.database.repository.UserRepository;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.topic.mapper.TopicMapper;
import com.kiloit.onlyadmin.model.topic.request.TopicRQ;
import com.kiloit.onlyadmin.model.topic.request.TopicUpdateRQ;
import com.kiloit.onlyadmin.util.FilterTopic;
import com.kiloit.onlyadmin.util.GetUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import static com.kiloit.onlyadmin.database.specification.TopicSpecification.filter;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService extends BaseService {
    private final CategoryRepository categoryRepository;
    private final FileMediaRepository fileMediaRepository;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserRepository userRepository;
    private final GetUser getUser;

    @Transactional(readOnly = false)
    public StructureRS createTopic (TopicRQ topicRQ){
        TopicEntity topicEntity = topicMapper.to(topicRQ);
        Optional<UserEntity> user = userRepository.findByEmailAndIsVerificationAndDeletedAtNull(getUser.getEmailUser(),true);
        if (user.isEmpty()) {
            throw new NotFoundException(MessageConstant.USER.USER_NOT_FOUND);
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByIDAndDeletedAtIsNull(topicRQ.getCategoryId());
        if(categoryEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        Optional<FileMedia> fileMedia = fileMediaRepository.findByIdAndDeletedAtIsNull(topicRQ.getFileMediaId());
        if(fileMedia.isEmpty()){
            throw new NotFoundException(MessageConstant.FILEMEDIA.FILE_MEDIA_NOT_FOUNT);
        }
        topicEntity.setCategory(categoryEntity.get());
        topicEntity.setUser(user.get());
        topicEntity.setFileMediaId(fileMedia.get());
        topicEntity = topicRepository.save(topicEntity);
        return response(topicMapper.to(topicEntity));
    }

    @Transactional(readOnly = true)
    public StructureRS getById(Long id) {
        Optional<TopicEntity> topicEntity = topicRepository.findTopic(id,getUser.getEmailUser(), getUser.getRoleUser());
        if (topicEntity.isEmpty()) {
           throw new NotFoundException(MessageConstant.TOPIC.TOPIC_NOT_FOUND);
        }
        System.out.println(topicEntity.get().getCategory());
        return response(topicMapper.to(topicEntity.get()));
    }

    @Transactional(readOnly = false)
    public StructureRS updateTopicById(Long id, TopicUpdateRQ topicUpdateRQ) {
        Optional<TopicEntity> topicEntity = topicRepository.findTopic(id,getUser.getEmailUser(), getUser.getRoleUser());
        if (topicEntity.isEmpty()) {
            throw new NotFoundException(MessageConstant.TOPIC.TOPIC_NOT_FOUND);
        }
        topicEntity.get().setName(topicUpdateRQ.getName());
        return  response(topicMapper.to(topicRepository.save(topicEntity.get())));
    }
    @Transactional(readOnly = false)
    public StructureRS deleteTopicByIdNotNull(Long id){
        Optional<TopicEntity> topicEntity = topicRepository.findTopic(id,getUser.getEmailUser(), getUser.getRoleUser());
        if (topicEntity.isEmpty())
            throw new BadRequestException(MessageConstant.USER.USER_NOT_FOUND);
        topicEntity.get().setDeletedAt(Instant.now());
        topicRepository.save(topicEntity.get());
        return response(HttpStatus.ACCEPTED,MessageConstant.TOPIC.TOPIC_HAVE_BEEN_DELETED);
    }
    public StructureRS getTopicList(FilterTopic filterTopic){
        Page<TopicEntity> topicList = topicRepository.findAll(filter(getUser.getRoleUser(),getUser.getEmailUser(),filterTopic.getQuery(),filterTopic.getUserId(),filterTopic.getCategoryId()),filterTopic.getPageable());
        return response(topicList.stream().map(topicMapper::toResponse),topicList);
    }


}
