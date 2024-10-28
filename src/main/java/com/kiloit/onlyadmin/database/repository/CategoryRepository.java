package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {

    @Query("select c from CategoryEntity c left join fetch c.user u left join fetch c.fileMediaId where c.id = :id and c.deletedAt is null")
    Optional<CategoryEntity> findByID(Long id);

    @Query("select c from CategoryEntity c left join fetch c.user u left join fetch c.fileMediaId f " +
            "where (( :role = 'Administrator' ) or ( :role != 'Administrator' and u.email = :email)) and c.id = :id and c.deletedAt is null")
    Optional<CategoryEntity> findCategory(@Param("id") Long id, @Param("email") String email, @Param("role") String role);

}