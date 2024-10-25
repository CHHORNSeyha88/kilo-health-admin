package com.kiloit.onlyadmin.database.entity;
import java.util.HashSet;
import java.util.Set;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    Set<RoleEntity> roles = new HashSet<>();
}
