package com.kiloit.onlyadmin.model.respone;

import lombok.Data;

import java.util.Date;

@Data
public class UserListRS {
    private String firstname;
    private String lastname;
    private String gender;
    private Date dob;
    private String photo;
    private Long roleId;
    private String roleName;
}
