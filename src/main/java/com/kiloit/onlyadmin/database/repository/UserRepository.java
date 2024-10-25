package com.kiloit.onlyadmin.database.repository;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> ,JpaSpecificationExecutor<UserEntity> {
    @SuppressWarnings("null")
    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role WHERE u.id = :id AND u.deletedAt IS NULL")
    Optional<UserEntity> findById(@Param("id")Long id);

    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role WHERE u.deletedAt IS NULL")
    Page<UserEntity> findAll(Specification<UserEntity> specification,PageRequest pageRequest);
    
    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role AS r WHERE u.email=:userName OR u.username=:userName")
    Optional<UserEntity> findUserOrEmail(@Param("userName") String userName);

    boolean existsByPhone(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phoneNumber);
    boolean existsByUsername(String username);

    boolean existsByEmailAndIsVerificationAndDeletedAt(@NotBlank(message = "Phone Number is required") String phoneNumber,boolean isVerification,LocalDateTime date);

}
