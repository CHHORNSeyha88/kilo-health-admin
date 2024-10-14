package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;

import java.util.Date;

@Data
public class UserListRS {
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private Date dob;
    private String photo;
    private Long roleId;
    private String roleName;
}
