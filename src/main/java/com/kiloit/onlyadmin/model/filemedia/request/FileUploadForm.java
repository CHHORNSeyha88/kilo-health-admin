package com.kiloit.onlyadmin.model.filemedia.request;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.kiloit.onlyadmin.exception.anotation.FileSize;
import com.kiloit.onlyadmin.exception.anotation.ValidImage;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FileUploadForm {
    @NotNull(message = "Please provide at least one file.")
    @FileSize(maxSizeInMB = 5*1024*1024 ,message = "Please upload only PNG or JPG images under 1MB.")
    @ValidImage(message = "Please upload a valid image file (PNG or JPG or JEPG).")
    private List<MultipartFile> files;

}
