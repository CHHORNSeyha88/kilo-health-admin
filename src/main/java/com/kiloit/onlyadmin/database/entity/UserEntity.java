package com.kiloit.onlyadmin.database.entity;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
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
    private LocalDate dob;
    private Boolean isVerification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private Set<PostEntity> postEntities = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostViewEntity> postViews ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<TopicEntity> topics ;

}
