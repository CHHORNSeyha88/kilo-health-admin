package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.repository.PostRepository;
import com.kiloit.onlyadmin.database.repository.TopicRepository;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import com.kiloit.onlyadmin.model.PostMapper.PostMapper;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.model.post.response.PostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService {
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

    public StructureRS PostCreate(PostCreateRequest request){
        PostEntity postEntityList = postMapper.toEntity(request);
        Optional<TopicEntity> topicEntity = topicRepository.findById(request.getTopic_id());
        if (topicEntity.isEmpty()) {
            throw new NotFoundException("Topic ID not found.");
        }
        postEntityList.setTopicEntity(topicEntity.get());
        return response(postMapper.toResponse(postRepository.save(postEntityList)));
    }

    public StructureRS PostUpdate(PostUpdateRequest request){
        PostEntity postEntityList = postMapper.toEntity(request);
        return response(postMapper.toUpdateResponse(postRepository.save(postEntityList)));
    }


    public StructureRS getPostDetail(){
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostDetailResponse> postDetailResponses = postEntities.stream()
                    .map(p -> 
                    {
                    PostDetailResponse postDetailResponse = new PostDetailResponse();
                    postDetailResponse.setTitle(p.getTitle());
                    postDetailResponse.setThumbnail(p.getThumbnail());
                    return postDetailResponse;
                 }
        ).toList();
        return response();
    }

}
