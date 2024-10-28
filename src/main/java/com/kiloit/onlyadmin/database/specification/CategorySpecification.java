package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {
    public static Specification<CategoryEntity> filter(String role,String email,String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.equalsIgnoreCase("ALL")) {
                Predicate namePredicate = cb.like(root.get("name"), "%" + query + "%");
                predicates.add(cb.or(namePredicate));
            }
            Join<CategoryEntity, UserEntity> joinUserEntity = root.join("user", JoinType.INNER);
            if (!"Administrator".equals(role)){
                predicates.add(cb.equal(joinUserEntity.get("email"),email));
            }
            predicates.add(cb.isNull(root.get("deletedAt")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}


