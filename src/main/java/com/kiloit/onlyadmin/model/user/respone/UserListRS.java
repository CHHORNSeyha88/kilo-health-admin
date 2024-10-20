package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserListRS {
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private LocalDate dob;
    private String photo;
    private Long roleId;
    private String roleName;
}
