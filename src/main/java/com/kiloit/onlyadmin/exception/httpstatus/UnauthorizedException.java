package com.kiloit.onlyadmin.exception.httpstatus;

public class UnauthorizedException extends RuntimeException {

    private final String message;
    private Object data;

    public UnauthorizedException(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public UnauthorizedException(String message) {
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
