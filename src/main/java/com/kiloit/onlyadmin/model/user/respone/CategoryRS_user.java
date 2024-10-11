package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryRS_user {
    private Integer id;
    private String firstname;
    private String lastname;
    private String gender;
    private Date dob;
    private String photo;
}
