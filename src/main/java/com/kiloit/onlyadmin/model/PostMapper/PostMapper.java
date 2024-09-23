package com.kiloit.onlyadmin.model.PostMapper;

import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.model.post.response.PostDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostEntity toEntity(PostCreateRequest request);

    PostDetailResponse toResponse(PostEntity postEntity);

    PostEntity toEntity(PostUpdateRequest request);
    PostUpdateRequest toUpdateResponse(PostEntity entity);

}