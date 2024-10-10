package com.kiloit.onlyadmin.model.respone;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class UserDetailRS {
    private String photo;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Date DoB;
    private Instant createdAt;
    private Instant updatedAt;

    private Long roleId;
}
