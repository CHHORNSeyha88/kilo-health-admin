package com.kiloit.onlyadmin.model.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    private Date DoB;
}
