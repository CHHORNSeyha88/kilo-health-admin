package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.CategoryEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {
    public static Specification<CategoryEntity> filter(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.equalsIgnoreCase("ALL")) {
                Predicate namePredicate = cb.like(root.get("name"), "%" + query + "%");
                predicates.add(cb.or(namePredicate));
            }


            predicates.add(cb.isNull(root.get("deletedAt")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}


