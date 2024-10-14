package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.PostEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<PostEntity> filter(String query,Boolean status,Long userId, Long categoryId , Long topicId) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.equalsIgnoreCase("ALL")) {
                Predicate namePredicate = cb.like(root.get("title"), "%" + query + "%");
                predicates.add(cb.or(namePredicate));
            }
            if (status != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), status);
                predicates.add(statusPredicate);
            }
            Join<PostEntity, UserEntity> joinUserEntity = root.join("userEntity", JoinType.INNER);
            if(userId != null && userId != 0){
                predicates.add(cb.equal(joinUserEntity.get("id"),userId));
            }
            Join<PostEntity, CategoryEntity> joinCategoryEntity = root.join("categoryEntity", JoinType.INNER);
            if(categoryId != null && categoryId!= 0){
                predicates.add(cb.equal(joinCategoryEntity.get("id"),categoryId));
            }

            Join<PostEntity, TopicEntity> joinTopicEntity = root.join("topicEntity", JoinType.INNER);
            if(topicId != null && topicId != 0){
                predicates.add(cb.equal(joinTopicEntity.get("id"),topicId));
            }
            predicates.add(cb.isNull(root.get("deletedAt")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}


