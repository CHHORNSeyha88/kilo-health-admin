package com.kiloit.onlyadmin.model.filemedia.response;
import lombok.Builder;

@Builder
public record FileMediaResponse(
    Long id,
    String fileName,
    String fileType,
    String fileUrl,
    Long fileSize
    ){
}
