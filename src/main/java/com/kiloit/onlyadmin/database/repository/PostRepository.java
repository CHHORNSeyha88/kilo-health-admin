package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long>, JpaSpecificationExecutor<PostEntity> {
    @Query("select p from PostEntity p join fetch TopicEntity t on t.id = p.topicEntity.id join fetch CategoryEntity c on c.id = p.categoryEntity.id join fetch UserEntity u on u.id = p.userEntity.id where p.id = :id and p.deletedAt is null")
    Optional<PostEntity> findPostById(Long id);
    Optional<PostEntity> findByIdAndDeletedAtNull(Long id);

    @Query("SELECT p FROM PostEntity p WHERE p.publicAt <= :now AND p.status = false")
    List<PostEntity> findPostsToPublish(Instant now);
}
