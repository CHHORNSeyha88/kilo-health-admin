package com.kiloit.onlyadmin.model.role.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPermissionItemRequest {
    private Long id;
    private Boolean status;
}
