package com.kiloit.onlyadmin.exception.anotation;

public class FileTypeException extends RuntimeException {
    private String fileName;
    private String contentType;

    public FileTypeException(String message, String fileName, String contentType) {
        super(message);
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }
}

