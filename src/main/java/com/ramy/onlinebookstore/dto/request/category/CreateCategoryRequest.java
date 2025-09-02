package com.ramy.onlinebookstore.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Create books's category")
public class CreateCategoryRequest {
    @NotBlank()
    @Schema(description = "Category's name", example = "Sci-fi", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
