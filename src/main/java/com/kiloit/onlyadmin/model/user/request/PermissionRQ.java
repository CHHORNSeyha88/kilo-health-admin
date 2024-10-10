package com.kiloit.onlyadmin.model.user.request;

import lombok.Data;

@Data
public class PermissionRQ {
    private String name;
    private String code;
    private String module;
}
