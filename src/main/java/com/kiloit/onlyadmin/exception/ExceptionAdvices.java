package com.kiloit.onlyadmin.exception;


import lombok.extern.slf4j.Slf4j;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sombath
 * create at 12/9/23 11:07 PM
 */

@ControllerAdvice
@Slf4j
public class ExceptionAdvices {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StructureRS> accessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNAUTHORIZED);
    }




    // validation

    @SuppressWarnings("null")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StructureRS> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");

        if(ex.getSupportedHttpMethods() != null)
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

        StructureRS structureRS = new StructureRS(
                HttpStatus.METHOD_NOT_ALLOWED,
                builder.toString()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<StructureRS> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage(), ex);

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        StructureRS structureRS = new StructureRS(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.toString()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @SuppressWarnings("null")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StructureRS> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage(), ex);

        String message = ex.getName() + " should be of type ";
        if(ex.getRequiredType() != null)
            message +=  ex.getRequiredType().getName();
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                message
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(BindException ex) {
        log.error(ex.getMessage(), ex);

        List<String> errors = new ArrayList<>();
        List<String> addedField = new ArrayList<>();

        for (FieldError error : ex.getFieldErrors()) {
            if (!addedField.contains(error.getField())) {
                addedField.add(error.getField());
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            if (!addedField.contains(error.getCode()))
                errors.add(error.getCode() + ": " + error.getDefaultMessage());
        }

        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }


    // custom

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StructureRS> badRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StructureRS> notFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        StructureRS structureRS = new StructureRS(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("File size exceeds the maximum limit! Please upload a smaller file.");
    }
}
