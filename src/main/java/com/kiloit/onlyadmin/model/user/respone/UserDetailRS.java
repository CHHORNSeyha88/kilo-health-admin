package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data; 
import java.time.LocalDate;
import com.kiloit.onlyadmin.model.role.response.RoleRS;

@Data
public class UserDetailRS {
    private Long id;
    private String photo;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    private RoleRS role;
}
