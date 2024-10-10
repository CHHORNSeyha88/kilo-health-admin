package com.kiloit.onlyadmin.model.user.request;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private LocalDate dob;
    private Long roleId;
}
