package com.kiloit.onlyadmin.service;
import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.MessageConstant;
import com.kiloit.onlyadmin.database.entity.*;
import com.kiloit.onlyadmin.database.repository.*;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.post.mapper.PostMapper;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.model.post.response.PostDetailResponse;
import com.kiloit.onlyadmin.util.FilterPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.kiloit.onlyadmin.database.specification.PostSpecification.filter;
import static com.kiloit.onlyadmin.util.CalculateWordAndTime.*;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;
    private final FileMediaRepository fileMediaRepository;
    private final PostViewRepository postViewRepository;
    private final PostMapper postMapper;

    @Transactional
    public StructureRS createPost(PostCreateRequest request) {
        PostEntity postEntityList = postMapper.toEntity(request);

        Optional<UserEntity> userEntity = userRepository.findById(request.getUser_id());
        if (userEntity.isEmpty()) {
            throw new NotFoundException("User ID not found");
        }
        Optional<TopicEntity> topicEntity = topicRepository.findById(request.getTopic_id());
        if (topicEntity.isEmpty()) {
            throw new NotFoundException("Topic ID not found.");
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(topicEntity.get().getCategory().getId());
        if (categoryEntity.isEmpty()) {
            throw new NotFoundException("Category Id not found");
        }
        Optional<FileMedia> fileMedia = fileMediaRepository.findById(request.getMediaId());
        if (fileMedia.isEmpty()) {
            throw new NotFoundException("Media Id not found");
        }
        if(request.getDescription() == null){
            throw new NotFoundException("Description not found");
        }
        int timeRead = calculateReadingTime(request.getDescription());
        postEntityList.setTime_read(timeRead);
        postEntityList.setStatus(request.isStatus());
        postEntityList.setPublicAt(request.getPublicAt());
        postEntityList.setUserEntity(userEntity.get());
        postEntityList.setTopicEntity(topicEntity.get());
        postEntityList.setCategoryEntity(categoryEntity.get());
        postEntityList.setFileMedia(fileMedia.get());

        return response(postMapper.toResponse(postRepository.save(postEntityList)));
    }
    @Transactional
    public StructureRS PostUpdate(Long id, PostUpdateRequest request) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("Post Id not found");
        }
        Optional<FileMedia> fileMedia = fileMediaRepository.findById(request.getMediaId());
        if(fileMedia.isEmpty()){
            throw new NotFoundException(MessageConstant.CATEGORY.CATEGORY_COULD_NOT_BE_FOUND);
        }
        postEntity.get().setTitle(request.getTitle());
        postEntity.get().setDescription(request.getDescription());
        postEntity.get().setFileMedia(fileMedia.get());
        PostUpdateRequest updateResponse = postMapper.toUpdateResponse(postRepository.save(postEntity.get()));
        return response(updateResponse);
    }

    @Transactional
    public StructureRS getPostDetail(Long id, Long userId) {
        Optional<PostEntity> postEntities = postRepository.findPostById(id);
        if (postEntities.isEmpty()) {
            throw new NotFoundException("Post Id not found");
        }
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("User Id not found");
        }
        PostViewEntity postViewEntity = new PostViewEntity();
        postViewEntity.setPost(postEntities.get());
        postViewEntity.setUser(userEntity.get());
        postViewEntity.setViewAt(Instant.now());
        Optional<PostViewEntity> checkView = postViewRepository.findByUserIdAndPostId(userEntity.get().getId(), postEntities.get().getId());
        if (checkView.isEmpty()) {
            postViewRepository.save(postViewEntity);
        }
        List<PostDetailResponse> postDetailResponses = postEntities.stream().map(postMapper::toResponse).toList();
        return response(postDetailResponses);
    }
    @Transactional(readOnly = true)
    public StructureRS getList(FilterPost filterPost){
        Page<PostEntity> postList = postRepository.findAll(filter(filterPost.getQuery(),filterPost.getStatus(),filterPost.getUserId(),filterPost.getCategoryId(),filterPost.getTopicId()), filterPost.getPageable());
        return response(postList.stream().map(postMapper::toResponse),postList);
    }

    @Transactional
    public StructureRS deletePostById(Long id){
        Optional<PostEntity> postEntity = postRepository.findByIdAndDeletedAtNull(id);
        if(postEntity.isEmpty()){
            throw new NotFoundException(MessageConstant.POST.POST_HAS_BEEN_DELETED);
        }
        postEntity.get().setDeletedAt(Instant.now());
        postRepository.save(postEntity.get());
        return response(HttpStatus.ACCEPTED,MessageConstant.POST.POST_HAS_BEEN_DELETED);
    }

}
