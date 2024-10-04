package com.kiloit.onlyadmin.database.entity;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    private String photo;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Date dob;
    private Boolean isVerification;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "userEntity")
    private Set<PostEntity> postEntities = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<PostViewEntity> postViews ;


}
