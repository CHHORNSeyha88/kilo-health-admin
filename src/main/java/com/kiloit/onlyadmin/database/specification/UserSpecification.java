package com.kiloit.onlyadmin.database.specification;

import com.kiloit.onlyadmin.database.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecification {
    public static Specification<UserEntity> filter(String query){
        return(root,cq,cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(cb.isNull(root.get("deletedDate"))));
            if (!Objects.equals(query, "ALL"))
                predicates.add(cb.or(cb.like(root.get("firstname"), query + "%"),
                        cb.like(root.get("lastname"), "%" + query + "%")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
