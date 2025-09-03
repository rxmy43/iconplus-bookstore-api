package com.ramy.onlinebookstore.repository;

import com.ramy.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT MAX(b.price), MIN(b.price), AVG(b.price) FROM Book b WHERE b.deletedAt IS NULL")
    List<Object[]> findPriceStatistics();

}
