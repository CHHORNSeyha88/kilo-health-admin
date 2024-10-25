package com.kiloit.onlyadmin.database.entity;
import java.time.LocalTime;
import com.kiloit.onlyadmin.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVerification extends BaseEntity {
    
    private String verifiedCode;
    private LocalTime expiryTime;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
