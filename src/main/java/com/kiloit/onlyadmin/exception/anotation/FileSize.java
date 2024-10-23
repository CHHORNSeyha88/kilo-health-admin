package com.kiloit.onlyadmin.exception.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {

    String message() default "File size exceeds the maximum allowed size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    long maxSize();

}