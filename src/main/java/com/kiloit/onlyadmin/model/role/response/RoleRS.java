package com.kiloit.onlyadmin.model.role.response;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRS {
    private Long id;
    private String name;
    private String code;
    private String module;
    private List<PermissionRS> permissions;
}
