package com.kiloit.onlyadmin.model.user.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleRQ {
    private String code;
    private String name;
    private List<Long> permissionsId = new ArrayList<>();
}
