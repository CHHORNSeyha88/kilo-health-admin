package com.kiloit.onlyadmin.database.entity;

import java.time.LocalTime;

import com.kiloit.onlyadmin.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetEntity extends BaseEntity{
    
    private String code;
    private LocalTime expiryTime;

    @OneToOne
    private UserEntity user;
}
