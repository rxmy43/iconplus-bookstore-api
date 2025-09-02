package com.ramy.onlinebookstore.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntitySpecification<T> implements Specification<T> {

    private final Map<String, Object> filters;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((key, value) -> {
            String[] parts = key.split("__");
            String field = parts[0];
            String op = parts.length > 1 ? parts[1] : "eq";
            Path<Object> path = root.get(field);

            switch (op) {
                case "eq" -> predicates.add(cb.equal(path, value));
                case "ne" -> predicates.add(cb.notEqual(path, value));
                case "like" -> predicates.add(cb.like(path.as(String.class), "%" + value + "%"));
                case "in" -> predicates.add(path.in((List<?>) value));
                case "gt", "lt", "gte", "lte" -> addComparablePredicate(predicates, cb, path, op, value);
                case "between" -> addBetweenPredicate(predicates, cb, path, value);
            }
        });

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
