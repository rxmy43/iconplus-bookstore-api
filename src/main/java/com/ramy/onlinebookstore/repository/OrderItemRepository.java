package com.ramy.onlinebookstore.repository;

import com.ramy.onlinebookstore.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT b.title, b.author, b.year, c.name, b.price, SUM(oi.quantity) " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "JOIN oi.book b " +
            "LEFT JOIN b.category c " +
            "WHERE o.createdAt BETWEEN :startDateTime AND :endDateTime " +
            "AND o.deletedAt IS NULL " +
            "AND o.status = 'PAID' " +
            "GROUP BY b.id, b.title, b.author, b.year, c.name, b.price " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopBestsellersRaw(@Param("startDateTime") LocalDateTime startDateTime,
                                         @Param("endDateTime") LocalDateTime endDateTime,
                                         Pageable pageable);

}
