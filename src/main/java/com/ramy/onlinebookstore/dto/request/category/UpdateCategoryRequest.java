package com.ramy.onlinebookstore.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Update category request")
public class UpdateCategoryRequest {
    @NotBlank()
    @Schema(description = "Category's name to be updated")
    private String name;
}
