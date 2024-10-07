package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.ListMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListMediaRepository extends JpaRepository<ListMediaEntity, Long> {
}