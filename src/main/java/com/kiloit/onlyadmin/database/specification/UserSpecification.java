package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<UserEntity> hasNotBeenDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<UserEntity> dynamicQuery(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (query != null && !query.equalsIgnoreCase("ALL")) {
                Predicate firstnamePredicate = cb.like(root.get("firstname"), "%" + query + "%");
                Predicate lastnamePredicate = cb.like(root.get("lastname"), "%" + query + "%");
    
                predicates.add(cb.or(firstnamePredicate, lastnamePredicate));
            }
    
            predicates.add(cb.isNull(root.get("deletedAt")));
    
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    
}
