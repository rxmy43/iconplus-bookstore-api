package com.ramy.onlinebookstore.specification;

import com.ramy.onlinebookstore.entity.Order;
import com.ramy.onlinebookstore.entity.enums.UserRole;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Map;
import java.util.Set;

public class OrderSpecification extends EntitySpecification<Order> {

    private final Long userId;
    private final UserRole userRole;

    public OrderSpecification(Map<String, Object> filters, Boolean deleted, Long userId, UserRole userRole) {
        super(filters, deleted);
        this.userId = userId;
        this.userRole = userRole;
    }

    @Override
    protected Set<String> getAllowedFieldOperators() {
        return Set.of();
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate basePredicate = super.toPredicate(root, query, cb);

        if (userRole.equals(UserRole.USER)) {
            Predicate userPredicate = cb.equal(root.get("user").get("id"), userId);
            return cb.and(basePredicate, userPredicate);
        }

        return basePredicate;
    }
}
