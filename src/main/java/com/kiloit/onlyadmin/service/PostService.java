package com.kiloit.onlyadmin.service;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.repository.PostRepository;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService {
    private final PostRepository postRepository;

    public StructureRS create(PostCreateRequest request){
        return response();
    }

}
