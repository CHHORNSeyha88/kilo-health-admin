package com.kiloit.onlyadmin.service.fileUpload.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
    String name,
    String uri,
    String contentType,
    Long size
) {
    
}
