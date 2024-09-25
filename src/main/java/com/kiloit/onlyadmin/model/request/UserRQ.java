package com.kiloit.onlyadmin.model.request;

import com.kiloit.onlyadmin.database.entity.RoleEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserRQ {
    private String photo;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date DoB;
    private Long roleId;

}
