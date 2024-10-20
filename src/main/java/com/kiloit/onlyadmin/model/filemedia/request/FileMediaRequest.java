package com.kiloit.onlyadmin.model.filemedia.request;


import com.kiloit.onlyadmin.constant.MessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FileMediaRequest {
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_NAME_IS_NULL)
    private String fileName;
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_TYPE_IS_NULL)
    private String fileType;
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_URL_IS_NULL)
    private String fileUrl;
    @NotNull(message = MessageConstant.FILEMEDIA.FILE_MEDIA_SIZE_IS_NULL)
    private Long fileSize;
}
