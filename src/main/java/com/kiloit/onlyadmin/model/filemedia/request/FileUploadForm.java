package com.kiloit.onlyadmin.model.filemedia.request;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.kiloit.onlyadmin.exception.anotation.FileSize;
import com.kiloit.onlyadmin.exception.anotation.FileType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FileUploadForm(
    @NotNull(message = "Files cannot be null")
    @FileSize(maxSize = 1024*1024)
    @FileType(allowedTypes = {"image/jpeg", "image/png"})
    List<MultipartFile> files
){}
