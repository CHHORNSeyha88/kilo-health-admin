package com.kiloit.onlyadmin.model.Category.request;

import lombok.Data;

@Data
public class CategoryRQ {
    private String name;
    private Long userId;
    private String fileName;
    private String fileType;
    private String filePath;
    private Long fileSize;

}
