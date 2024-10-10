package com.kiloit.onlyadmin.model.role.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoleRS {
    private Long id;
    private String name;
    private String code;
    private String module;
    private List<PermissionRS> permissions = new ArrayList<>();
}
