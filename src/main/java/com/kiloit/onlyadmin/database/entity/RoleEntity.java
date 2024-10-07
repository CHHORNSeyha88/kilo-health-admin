package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity implements GrantedAuthority{
    private String code;
    private String name;
    private String module;

    @ManyToMany
    @JoinTable(
            name = "role_has_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    List<PermissionEntity> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    List<UserEntity> users = new ArrayList<>();

    @Override
    public String getAuthority() {
        return "ROLE_"+name;
    }
}
