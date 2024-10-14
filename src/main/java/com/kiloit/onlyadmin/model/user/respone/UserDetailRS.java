package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;
import java.util.Date;
import com.kiloit.onlyadmin.model.role.response.RoleRS;

@Data
public class UserDetailRS {
    private String photo;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Date dob;
    private RoleRS role;
}
