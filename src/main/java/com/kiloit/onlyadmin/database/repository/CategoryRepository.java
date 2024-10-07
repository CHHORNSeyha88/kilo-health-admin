package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}