package com.kiloit.onlyadmin.exception.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class FileSizeValidator implements ConstraintValidator<FileSize,List<MultipartFile>> {
    private static final long MB = 1024 * 1024;
    private long maxSizeInBytes;

    @Override
    public void initialize(FileSize fileSize) {
        this.maxSizeInBytes = fileSize.maxSize();
    }
    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) return false;
        for (MultipartFile file : files) {
            if (file.getSize() > maxSizeInBytes) throw new FileTypeException("File size exceeds the maximum limit of "+(maxSizeInBytes / MB) + " MB. Please upload only PNG or JPG images under 1MB.",file.getOriginalFilename(), file.getContentType());
        }
        return true;
    }

}
    
