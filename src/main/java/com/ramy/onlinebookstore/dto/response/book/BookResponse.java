package com.ramy.onlinebookstore.dto.response.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Book response")
public class BookResponse {
    @Schema(description = "Book ID", example = "1")
    private Long id;

    @Schema(description = "Book's title", example = "The Hobbit")
    private String title;

    @Schema(description = "Book's author", example = "J.R.R. Tolkien")
    private String author;

    @Schema(description = "Price of the book", example = "150000.00")
    private BigDecimal price;

    @Schema(description = "Publishing year", example = "1937")
    private int year;

    @Schema(description = "Current Stock", example = "200")
    private int stock;

    @Schema(description = "Category of the book", example = "Fiction")
    private String category;

    @Schema(description = "Image base64", example = "base64:...")
    private String imageBase64;

}
