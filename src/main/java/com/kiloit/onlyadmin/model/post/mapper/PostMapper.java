package com.kiloit.onlyadmin.model.post.mapper;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS;
import com.kiloit.onlyadmin.model.Category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.model.post.response.PostDetailResponse;
import com.kiloit.onlyadmin.model.post.response.PostListResponse;
import com.kiloit.onlyadmin.model.post.response.PostUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostEntity toEntity(PostCreateRequest request);


    @Mapping(target = "userListResponse",source = "userEntity")
    @Mapping(target = "topicRSById", source = "topicEntity")
    @Mapping(target = "categoryRS",source = "categoryEntity")
    PostDetailResponse toResponse(PostEntity postEntity);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "mediaId", source = "fileMediaId.id")
    CategoryRS_List from(CategoryEntity categoryEntity);

    PostEntity toEntity(PostUpdateRequest request);
    @Mapping(target = "title",source = "title")
    @Mapping(target = "description" , source = "description")
    @Mapping(target = "mediaId", source = "fileMedia.id")
    PostUpdateRequest toUpdateResponse(PostEntity entity);

    @Mapping(target = "userName",source = "userEntity.firstname")
    @Mapping(target = "topicName", source = "topicEntity.name")
    @Mapping(target = "categoryName", source = "categoryEntity.name")
    PostListResponse from(PostEntity postEntity);
}