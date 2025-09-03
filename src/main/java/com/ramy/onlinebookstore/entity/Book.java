package com.ramy.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramy.onlinebookstore.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity()
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "TEXT", name = "image_base64")
    private String imageBase64;
}
