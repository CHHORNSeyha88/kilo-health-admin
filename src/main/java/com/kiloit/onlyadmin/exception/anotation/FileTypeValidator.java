package com.kiloit.onlyadmin.exception.anotation;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileType, List<MultipartFile>> {

    private String[] allowedTypes;

    @Override
    public void initialize(FileType fileType) {
        this.allowedTypes = fileType.allowedTypes();
    }

    @SuppressWarnings("null")
    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        boolean valid = true;
        String contentType;
        if (files == null || files.isEmpty()) return true;
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) valid=false;
            contentType = file.getContentType();
            if (contentType == null)  throw new FileTypeException("File type cannot be determined", file.getOriginalFilename(), null);
            else if (!isAllowedType(contentType)) throw new FileTypeException("Invalid file type: " + contentType+","+" Please upload a valid image file (PNG or JPG or JEPG)", file.getOriginalFilename(), contentType);
        }
        return valid;
    }

    private boolean isAllowedType(String contentType) {
        for (String allowedType : allowedTypes) {
            if (allowedType.equalsIgnoreCase(contentType)) return true;
        }
        return false;
    }
}

