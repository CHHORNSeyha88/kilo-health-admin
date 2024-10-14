package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.PostViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostViewRepository extends JpaRepository<PostViewEntity, Long > {
    Optional<PostViewEntity> findByUserIdAndPostId(long userId,Long postId);
}
