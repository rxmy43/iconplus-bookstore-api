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
    @Schema(description = "Category ID", example = "1")
    private Long id;

    @Schema(description = "Category's name", example = "Action Comedy")
    private String name;
}
