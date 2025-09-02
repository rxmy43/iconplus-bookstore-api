package com.ramy.onlinebookstore.dto.response.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Category's response")
public class CategoryResponse {
    @Schema(description = "Category ID")
    private Long id;

    @Schema(description = "Category's name")
    private String name;
}
