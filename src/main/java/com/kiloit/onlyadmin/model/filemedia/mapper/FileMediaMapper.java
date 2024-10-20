package com.kiloit.onlyadmin.model.filemedia.mapper;

import com.kiloit.onlyadmin.database.entity.FileMedia;
import com.kiloit.onlyadmin.model.filemedia.request.FileMediaRequest;
import com.kiloit.onlyadmin.model.filemedia.response.FileMediaResponse;
import org.mapstruct.Mapper;

@Mapper
public interface FileMediaMapper {
    FileMedia toFileMedia(FileMediaRequest request);
    FileMediaResponse toFileMediaResponse(FileMedia fileMedia);

}
