package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleRS {
    private Long id;
    private String name;
    private String code;
    private List<PermissionRS> permissions = new ArrayList<>();
}
