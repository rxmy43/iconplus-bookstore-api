package com.ramy.onlinebookstore.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Bestseller response shows top 3 books based on time period")
public class BestsellerResponse {

    @Schema(description = "Book's title")
    private String title;

    @Schema(description = "Book's author")
    private String author;

    @Schema(description = "Book's published year")
    private String year;

    @Schema(description = "Book's category name")
    private String category;

    @Schema(description = "Book's price")
    private BigDecimal price;

    @Schema(description = "Total book sold")
    private Integer totalBookSold;
}
