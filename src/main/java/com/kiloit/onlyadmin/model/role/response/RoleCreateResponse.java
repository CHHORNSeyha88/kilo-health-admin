package com.kiloit.onlyadmin.model.role.response;
import lombok.Builder;

@Builder
public record RoleCreateResponse (
     Long id,
     String name,
     String code,
     String module
){}
