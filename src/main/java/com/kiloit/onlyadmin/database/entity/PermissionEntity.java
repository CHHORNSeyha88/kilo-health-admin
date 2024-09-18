package com.kiloit.onlyadmin.database.entity;

import java.util.ArrayList;
import java.util.List;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class PermissionEntity extends BaseEntity {
    private String code;
    private String name;
    private String module;

    @ManyToMany(mappedBy = "permissions")
    List<RoleEntity> roles = new ArrayList<>();
}
