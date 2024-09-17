package com.kiloit.onlyadmin.exception.httpstatus;

public class BadRequestException extends RuntimeException {

    private final String message;
    private Object data;

    public BadRequestException(String message, String messageKey, Object data) {
        this.message = message;
        this.data = data;
    }
    public BadRequestException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
    
    public Object getData() {
        return data;
    }
}
