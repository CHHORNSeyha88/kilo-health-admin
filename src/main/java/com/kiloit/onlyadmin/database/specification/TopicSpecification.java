package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.TopicEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TopicSpecification {
    public static Specification<TopicEntity> filter(String role,String email,String query,Long userId,Long categoryId){
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(query!=null && !query.equalsIgnoreCase("ALL")){
                Predicate predicateName = cb.like(root.get("name"), "%" + query + "%");
                predicates.add(cb.or(predicateName));
            }
            if(categoryId != null && categoryId != 0){
                Join<TopicEntity, CategoryEntity> joinCategoryEntity = root.join(("category"), JoinType.INNER);
                predicates.add(cb.equal(joinCategoryEntity.get("id"),categoryId));
            }
            Join<TopicEntity, UserEntity> joinUserEntity = root.join(("user"), JoinType.INNER);
            if ("Administrator".equals(role)) {
                if(userId != null && userId != 0){
                    predicates.add(cb.equal(joinUserEntity.get("id"),userId));
                }
            }else {
                predicates.add(cb.equal(joinUserEntity.get("email"),email));
            }
            predicates.add(cb.isNull(root.get("deletedAt")));

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }
}
