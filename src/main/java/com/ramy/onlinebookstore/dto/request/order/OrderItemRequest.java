package com.ramy.onlinebookstore.dto.request.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Single order item")
public class OrderItemRequest {
    @Schema(description = "Book ID", example = "20")
    private Long bookId;

    @Schema(description = "Quantity", example = "3")
    private Integer quantity;
}
