package com.kiloit.onlyadmin.model.filemedia.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FileMediaResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long fileSize;
}
