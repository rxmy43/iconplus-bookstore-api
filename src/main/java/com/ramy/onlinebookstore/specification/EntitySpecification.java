package com.ramy.onlinebookstore.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class EntitySpecification<T> implements Specification<T> {

    private final Map<String, Object> filters;
    private final Boolean deleted;

    protected abstract Set<String> getAllowedFieldOperators();

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {

        if (query.getResultType() != Long.class) { // skip count query
            for (var field : root.getJavaType().getDeclaredFields()) {
                if (field.isAnnotationPresent(ManyToOne.class) &&
                        field.getAnnotation(ManyToOne.class).fetch() == FetchType.LAZY) {
                    root.fetch(field.getName(), JoinType.LEFT);
                } else if (field.isAnnotationPresent(OneToOne.class) &&
                        field.getAnnotation(OneToOne.class).fetch() == FetchType.LAZY) {
                    root.fetch(field.getName(), JoinType.LEFT);
                } else if (field.isAnnotationPresent(OneToMany.class) &&
                        field.getAnnotation(OneToMany.class).fetch() == FetchType.LAZY) {
                    root.fetch(field.getName(), JoinType.LEFT);
                } else if (field.isAnnotationPresent(ManyToMany.class) &&
                        field.getAnnotation(ManyToMany.class).fetch() == FetchType.LAZY) {
                    root.fetch(field.getName(), JoinType.LEFT);
                }
            }
        }

        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((key, value) -> {
            // Skip non-whitelist fields and operators to avoid invalid filters e.g. name__gte=5
            if (!getAllowedFieldOperators().contains(key)) return;

            String[] parts = key.split("__");
            String field = parts[0];
            String op = parts.length > 1 ? parts[1] : "eq";

            String[] fieldParts = field.split("\\.");
            Path<Object> path = (Path<Object>) root;
            for (String f : fieldParts) {
                path = path.get(f);
            }


            switch (op) {
                case "eq" -> predicates.add(cb.equal(path, value));
                case "ne" -> predicates.add(cb.notEqual(path, value));
                case "like" -> predicates.add(cb.like(path.as(String.class), "%" + value + "%"));
                case "in" -> predicates.add(path.in((List<?>) value));
                case "gt", "lt", "gte", "lte" -> addComparablePredicate(predicates, cb, path, op, value);
                case "between" -> addBetweenPredicate(predicates, cb, path, value);
            }
        });

        if (deleted != null) {
            Path<Object> path = root.get("deletedAt");
            if (deleted) {
                predicates.add(cb.isNotNull(path));
            } else {
                predicates.add(cb.isNull(path));
            }
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Y extends Comparable> void addComparablePredicate(List<Predicate> predicates,
            CriteriaBuilder cb, Path<?> path, String op, Object value) {

        Path<Y> comparablePath = (Path<Y>) path;

        switch (op) {
            case "gt" -> predicates.add(cb.greaterThan(comparablePath, (Y) value));
            case "lt" -> predicates.add(cb.lessThan(comparablePath, (Y) value));
            case "gte" -> predicates.add(cb.greaterThanOrEqualTo(comparablePath, (Y) value));
            case "lte" -> predicates.add(cb.lessThanOrEqualTo(comparablePath, (Y) value));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <Y extends Comparable> void addBetweenPredicate(List<Predicate> predicates,
            CriteriaBuilder cb, Path<?> path, Object value) {

        if (value instanceof List<?> range && range.size() == 2) {
            Path<Y> comparablePath = (Path<Y>) path;
            predicates.add(cb.between(comparablePath, (Y) range.get(0), (Y) range.get(1)));
        }
    }

}
