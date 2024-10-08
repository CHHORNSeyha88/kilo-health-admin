package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("select c from CategoryEntity c join fetch c.user")
    List<CategoryEntity> findAllFecthById();
}