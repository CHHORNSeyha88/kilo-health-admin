package com.kiloit.onlyadmin.database.specification;
import com.kiloit.onlyadmin.database.entity.FileMedia;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class FileMediaSpecification {
    public static Specification<FileMedia> hasNotBeenDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }
    public static Specification<FileMedia> filter(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if ("ALL".equalsIgnoreCase(query) || query == null) predicates.add(cb.isNull(root.get("deletedAt")));
            else {
                Predicate namePredicate = cb.like(root.get("fileName"), "%" + query + "%");
                predicates.add(cb.or(namePredicate));
                predicates.add(cb.isNull(root.get("deletedAt")));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}