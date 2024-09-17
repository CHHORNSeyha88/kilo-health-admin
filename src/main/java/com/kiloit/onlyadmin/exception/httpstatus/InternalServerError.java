package com.kiloit.onlyadmin.exception.httpstatus;

public class InternalServerError extends RuntimeException {

    private final String message;
    private Object data;

    public InternalServerError(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public InternalServerError(String message) {
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
