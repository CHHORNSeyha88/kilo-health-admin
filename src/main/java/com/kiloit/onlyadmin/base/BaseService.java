package com.kiloit.onlyadmin.base;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

public class BaseService {

    protected StructureRS response() {
        return new StructureRS();
    }

    protected <T> StructureRS response(Object object) {
        return new StructureRS(object);
    }

    protected <T> StructureRS response(T data, PagingRS pagingRS) {
        return new StructureRS(data, pagingRS);
    }

    protected <T, S> StructureRS response(T data, Page<S> page) {
        return new StructureRS(data, new PagingRS(page));
    }

    protected StructureRS response(HttpStatus httpStatus, String message) {
        return new StructureRS(httpStatus, message);
    }

    protected <T> StructureRS response(HttpStatus httpStatus, String message, T data) {
        return new StructureRS(httpStatus, message, data);
    }

}
