package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.Entity;
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
}
