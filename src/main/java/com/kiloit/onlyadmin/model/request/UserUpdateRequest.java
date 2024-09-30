package com.kiloit.onlyadmin.model.request;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String photo;
    @NotBlank(message = "First Name is required")
    private String firstname;
    @NotBlank(message = "Last Name is required")
    private String lastname;
    @NotBlank(message = "Phone Number is required")
    private String phone;
    private String address;
    @NotBlank(message = "Gender is required")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date dob;
    private Long roleId;
}
