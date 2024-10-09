package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("select c from CategoryEntity c left join fetch FileMedia m on c.fileMediaId.id = m.id where c.id = :id and c.deletedAt is null")
    Optional<CategoryEntity> findByID(Long id);

}