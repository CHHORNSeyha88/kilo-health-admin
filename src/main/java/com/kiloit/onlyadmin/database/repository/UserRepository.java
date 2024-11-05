package com.kiloit.onlyadmin.database.repository;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
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

    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role AS r WHERE u.id = :id AND (u.deletedAt IS NULL AND u.isVerification=TRUE) AND r.name != 'Administrator' ")
    Optional<UserEntity> findById(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role AS r WHERE (u.deletedAt IS NULL AND u.isVerification=TRUE) AND r.name != 'Administrator'")
    Page<UserEntity> findAll(Specification<UserEntity> specification, PageRequest pageRequest);

    @Query("SELECT u FROM UserEntity AS u JOIN FETCH u.role AS r WHERE u.email=:userName OR u.username=:userName")
    Optional<UserEntity> findUserOrEmail(@Param("userName") String userName);

    boolean existsByPhone(String phoneNumber);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmailAndIsVerificationAndDeletedAtNull(String email,Boolean isVerification);

    boolean existsByUsername(String username);

    boolean existsByEmailAndIsVerificationAndDeletedAtNull(String email, Boolean isVerification);

    @Query("select u from UserEntity u left join fetch u.role r where u.email = :email")
    Optional<UserEntity> findByEmailAndDeletedAt(String email);
}