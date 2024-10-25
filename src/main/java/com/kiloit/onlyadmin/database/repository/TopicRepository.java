package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> , JpaSpecificationExecutor<TopicEntity> {
    Optional<TopicEntity> findByIdAndDeletedAtNull(Long id);

    @Query("select t from TopicEntity t left join fetch t.fileMediaId f left join fetch t.category left join fetch t.user u where t.id = :id and t.deletedAt is null ")
    Optional<TopicEntity> findTopicEntityById(Long id);
}
