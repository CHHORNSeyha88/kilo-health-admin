package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
