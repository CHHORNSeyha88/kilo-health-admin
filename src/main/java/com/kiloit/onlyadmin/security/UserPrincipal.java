package com.kiloit.onlyadmin.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kiloit.onlyadmin.database.entity.PermissionEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class UserPrincipal implements UserDetails, Principal {

    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String phone;
    private String address;
    private String roleName;
    private Long roleId;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(UserEntity u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.email = u.getEmail();
        this.phone = u.getPhone();
        this.address = u.getAddress();
        this.roleId = u.getRole().getId();
        this.roleName = u.getRole().getName();
        this.password = u.getPassword();
        this.authorities = Collections.emptyList();

        Set<String> permissions = u.getRole().getPermissions().stream().map(PermissionEntity::getName).collect(Collectors.toSet());
        this.authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public UserPrincipal(JwtAuthenticationToken jwt) {

        Map<String, Object> claims = jwt.getTokenAttributes();

        this.id = (Long) claims.get("id");
        this.username = (String) claims.get("username");
        this.email = (String) claims.get("email");
        this.phone = (String) claims.get("phone");
        this.address = (String) claims.get("address");
        this.avatar = (String) claims.get("avatar");
        this.roleName = (String) claims.get("roleName");
        this.roleId = (Long) claims.get("roleId");

    }

    public static UserPrincipal build(UserEntity user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal build(JwtAuthenticationToken jwt) {
        return new UserPrincipal(jwt);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return this.username;
    }
}
