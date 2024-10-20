package com.kiloit.onlyadmin.model.user.respone;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserListResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String gender;
    private LocalDate dob;
    private String photo;
}
