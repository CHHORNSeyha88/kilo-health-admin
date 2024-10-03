package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> ,JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByIdAndDeletedAtNull(Long id);
    
    // @Query("SELECT u FROM UserEntity u WHERE u.deletedAt IS NULL")
    // Page<UserEntity> findAll(Specification<UserEntity> specification,PageRequest pageRequest);

    boolean existsByPhone(
            String phoneNumber);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phoneNumber);
}
