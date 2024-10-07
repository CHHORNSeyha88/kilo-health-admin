package com.kiloit.onlyadmin.model.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserRQ {
    private String photo;
    @NotBlank(message = "First Name is required!!!")
    private String firstname;
    @NotBlank(message = "Last Name is required!!!")
    private String lastname;
    @NotBlank(message = "User Name is required!!!")
    private String username;
    @NotBlank(message="Password is required")
    @Size(min=6,message = "Password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    ,message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter")
    private String password;
    @NotBlank(message = "Email is required!!!")
    private String email;
    @NotBlank(message="Phone number is required")
    @Size(min=9,max=10,message = "Phone number must be between 9 to 10 digits")
    private String phone;
    @NotBlank(message = "Address is required!!!")
    private String address;
    @NotBlank(message = "Gender is required!!!")
    private String gender;
    @NotNull(message = "Date of Birth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Positive(message = "RoleId must be negative number!!!")
    private Long roleId;

}
