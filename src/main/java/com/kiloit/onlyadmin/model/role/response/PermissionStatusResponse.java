package com.kiloit.onlyadmin.model.role.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionStatusResponse{
    private Long id;
    private String name;
    private String module;
    private boolean status;
}
