package com.ramy.onlinebookstore.repository;

import com.ramy.onlinebookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) " +
            "FROM Order o " +
            "WHERE o.createdAt BETWEEN :startDateTime AND :endDateTime " +
            "AND o.deletedAt IS NULL " +
            "AND o.status = 'PAID'")
    BigDecimal sumRevenueBetweenDates(@Param("startDateTime")LocalDateTime startDateTime, @Param("endDateTime")LocalDateTime endDateTime);

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.createdAt BETWEEN :startDateTime AND :endDateTime " +
            "AND o.deletedAt IS NULL " +
            "AND o.status = 'PAID'")
    Long sumBooksSoldBetweenDates(@Param("startDateTime")LocalDateTime startDateTime, @Param("endDateTime")LocalDateTime endDateTime);
}
