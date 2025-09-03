package com.ramy.onlinebookstore.dto.request.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Schema(description = "Request DTO for updating a Book")
public class UpdateBookRequest {
    @Schema(description = "Title of the book", example = "The Hobbit Updated", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(description = "Author of the book", example = "J.R.R. Tolkien", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String author;

    @Schema(description = "Price of the book", example = "175000.00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BigDecimal price;

    @Schema(description = "Stock available", example = "25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer stock;

    @Schema(description = "Publishing year", example = "1938", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer year;

    @Schema(description = "Category ID of the book", example = "2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long categoryId;

    @Schema(description = "Image file of the book cover", type = "string", format = "binary", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private MultipartFile image;
}
