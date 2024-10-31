package com.kiloit.onlyadmin.database.repository;

import com.kiloit.onlyadmin.database.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long>, JpaSpecificationExecutor<PostEntity> {
    @Query("select p from PostEntity p left join fetch p.userEntity u left join fetch p.topicEntity t left join fetch p.categoryEntity c left join fetch p.fileMedia f where p.id = :id and p.deletedAt is null")
    Optional<PostEntity> findPostByIdAndAndDeletedAtIsNull(Long id);


    Optional<PostEntity> findByIdAndDeletedAtNull(Long id);

    @Query("select p from PostEntity p left join fetch p.userEntity u left join fetch p.fileMedia f left join fetch p.categoryEntity c left join fetch p.topicEntity t " +
            "where(( :role = 'Administrator' ) or ( :role != 'Administrator' and u.email = :email)) and p.id = :id and p.deletedAt is null and t.deletedAt is null and c.deletedAt is null")
    Optional<PostEntity> findPost(@Param("id") Long id,@Param("email") String email,@Param("role") String role);

}
