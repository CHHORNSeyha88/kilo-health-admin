package com.kiloit.onlyadmin.model.request;

import lombok.Data;

@Data
public class PermissionRQ {
    private String name;
    private String code;
    private String module;
}
