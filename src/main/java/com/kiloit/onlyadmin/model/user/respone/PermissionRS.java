package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;

@Data
public class PermissionRS {
    private Long id;
    private String name;
    private String code;
    private String module;
}
