package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
