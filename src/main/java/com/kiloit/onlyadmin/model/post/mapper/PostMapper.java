package com.kiloit.onlyadmin.model.post.mapper;
import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.model.category.respone.CategoryRS_List;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.model.post.response.PostDetailResponse;
import com.kiloit.onlyadmin.model.post.response.PostListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target="fileMedia",ignore=true),
        @Mapping(target = "categoryEntity",ignore = true),
        @Mapping(target = "postView",ignore = true),
        @Mapping(target = "publicAt",ignore = true),
        @Mapping(target="status",ignore = true),
        @Mapping(target = "topicEntity",ignore = true),
        @Mapping(target="userEntity",ignore=true),
        @Mapping(target = "time_read",ignore = true)
    })
    PostEntity toEntity(PostCreateRequest request);

    @Mappings({
        @Mapping(target = "user",source = "userEntity"),
        @Mapping(target = "topic", source = "topicEntity"),
        @Mapping(target = "category",source = "categoryEntity"),
            @Mapping(target = "fileMedia", source = "fileMedia"),
    })
    PostDetailResponse toResponse(PostEntity postEntity);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "url", source = "fileMediaId.fileUrl")
    CategoryRS_List from(CategoryEntity categoryEntity);

    @Mappings({
        @Mapping(target = "createdAt",ignore = true),
        @Mapping(target = "deletedAt",ignore = true),
        @Mapping(target="id",ignore = true),
        @Mapping(target = "modifiedAt",ignore = true),
        @Mapping(target="fileMedia",ignore=true),
        @Mapping(target = "categoryEntity",ignore = true),
        @Mapping(target = "postView",ignore = true),
        @Mapping(target = "publicAt",ignore = true),
        @Mapping(target="status",ignore = true),
        @Mapping(target = "topicEntity",ignore = true),
        @Mapping(target="userEntity",ignore=true),
        @Mapping(target = "time_read",ignore = true)
    })
    PostEntity toEntity(PostUpdateRequest request);

    @Mappings({
        @Mapping(target = "title",source = "title"),
        @Mapping(target = "description" , source = "description"),
        @Mapping(target = "mediaId", source = "fileMedia.id")
    })
    PostUpdateRequest toUpdateResponse(PostEntity entity);

    @Mapping(target = "userName",source = "userEntity.firstname")
    @Mapping(target = "topicName", source = "topicEntity.name")
    @Mapping(target = "categoryName", source = "categoryEntity.name")
    @Mapping(target = "url",source = "fileMedia.fileUrl")
    PostListResponse from(PostEntity postEntity);
}