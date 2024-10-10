package com.kiloit.onlyadmin.database.entity;

import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity implements GrantedAuthority{
    private String code;
    private String name;
    private String module;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_has_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    Set<PermissionEntity> permissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    Set<UserEntity> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return "ROLE_"+name;
    }
}
