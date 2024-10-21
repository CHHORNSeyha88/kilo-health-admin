package com.kiloit.onlyadmin.exception.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author Sombath
 * create at 30/9/23 1:05 PM
 */
    public class FileSizeValidator implements ConstraintValidator<FileSize, List<MultipartFile>> {
    
        private static final long MB = 1024 * 1024;  // Conversion factor for MB to bytes
        private long maxSizeInBytes;  // Will hold the maximum size in bytes
    
        @Override
        public void initialize(FileSize fileSize) {
            // Convert the max size in MB to bytes
            this.maxSizeInBytes = fileSize.maxSizeInMB();
        }
    
        @Override
        public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
    
            if (multipartFiles == null) {
                return true; // No files to validate
            }
    
            // Validate each file in the list
            for (MultipartFile file : multipartFiles) {
                if (!isValidFile(file, constraintValidatorContext)) {
                    return false;
                }
            }
    
            return true;  // All files are valid
        }
    
        // Validate a single file
        private boolean isValidFile(MultipartFile file, ConstraintValidatorContext context) {
            boolean result = true;
            if (file == null || file.isEmpty()) {
                return true; // Allow empty files, or no file
            }
    
            // Check if the file exceeds the max allowed size
            if (file.getSize() > maxSizeInBytes) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "File size exceeds the maximum limit of "+(maxSizeInBytes / MB) + " MB."
                ).addConstraintViolation();
                result = false;
            }
    
            return result;  // File is valid
        }
    }
    
