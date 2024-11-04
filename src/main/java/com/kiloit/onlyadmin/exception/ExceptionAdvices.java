package com.kiloit.onlyadmin.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.exception.anotation.FileTypeException;
import com.kiloit.onlyadmin.exception.httpstatus.BadRequestException;
import com.kiloit.onlyadmin.exception.httpstatus.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class ExceptionAdvices extends BaseController {

    @SuppressWarnings("null")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StructureRS> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
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
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(BindException ex) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StructureRS> badRequestException(BadRequestException ex) {
        StructureRS structureRS = new StructureRS(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StructureRS> notFoundException(NotFoundException ex) {
        StructureRS structureRS = new StructureRS(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return new ResponseEntity<>(structureRS, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<StructureRS> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return new ResponseEntity<>(new StructureRS(HttpStatus.PAYLOAD_TOO_LARGE,"Validation failed","File too large! Maximum allowed size is 1MB."),HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructureRS> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String,String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName,errorMessage);
        }
        return new ResponseEntity<>(new StructureRS(HttpStatus.BAD_REQUEST,"Validation failed",errors),HttpStatus.BAD_REQUEST);
     
    }

    @ExceptionHandler(FileTypeException.class)
    public ResponseEntity<StructureRS> handleFileTypeException(FileTypeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("fileName", ex.getFileName());
        errorResponse.put("contentType", ex.getContentType() != null ? ex.getContentType() : "unknown");
        errorResponse.put("message", ex.getMessage());
        
        return new ResponseEntity<>(new StructureRS(HttpStatus.BAD_REQUEST,"Validation failed",errorResponse), HttpStatus.BAD_REQUEST);
    }

    // Handler for 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<StructureRS> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        return new ResponseEntity<>(new StructureRS(HttpStatus.FORBIDDEN,"Forbidden","You do not have permission to access this resource."), HttpStatus.FORBIDDEN);
    }

    //Handler for 401 Unauthorized
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<StructureRS> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        return new ResponseEntity<>(new StructureRS(HttpStatus.UNAUTHORIZED,"Unauthorized","Authentication is required to access this resource."), HttpStatus.UNAUTHORIZED);
    }

}