package com.kiloit.onlyadmin.model.filemedia.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;

@Mapper
public interface FileMediaMapper {

    FileMediaResponse toFileMediaResponse(FileMedia fileMedia);

    @Mappings({
        @Mapping(target="createdAt",ignore = true),
        @Mapping(target="deletedAt",ignore = true),
        @Mapping(target="modifiedAt",ignore = true)
    })
    FileMedia fromFileMediaResponse(FileMediaResponse fileMediaResponse);

}
