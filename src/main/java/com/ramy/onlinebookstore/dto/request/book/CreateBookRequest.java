package com.ramy.onlinebookstore.dto.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Schema(description = "Request DTO for creating a new Book")
public class CreateBookRequest {
    @Schema(description = "Title of the book", example = "The Hobbit", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.REQUIRED)
    private String author;

    @Schema(description = "Price of the book", example = "150000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(description = "Stock available", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
    private int stock;

    @Schema(description = "Publishing year", example = "1937", requiredMode = Schema.RequiredMode.REQUIRED)
    private int year;

    @Schema(description = "Category ID of the book", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(description = "Image file of the book cover", type = "string", format = "binary", requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile image;
}
