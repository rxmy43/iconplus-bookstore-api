package com.ramy.onlinebookstore.dto.request.pagination;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Pagination and filtering request")
public class PaginationRequest {
    @Schema(description = "Page number (0-based)", example = "0")
    @Builder.Default
    private int page = 0;

    @Schema(description = "Number of items per page", example = "10")
    @Builder.Default
    private int size = 10;

    @Schema(description = "Field name to sort by", example = "id")
    @Builder.Default
    private String sortField = "id";

    @Schema(description = "Sort direction: asc or desc", example = "asc")
    @Builder.Default
    private String sortDir = "asc";

    @Schema(description = "Filters map: key format 'field__operator', value is filter value. " +
            "Supported operators: eq, ne, gt, lt, gte, lte, like, in, between. " +
            "For 'in' use a list of values, for 'between' use a list with 2 elements (range).", example = "{\"name__like\":\"book\",\"price__gte\":100,\"createdDate__between\":[\"2025-01-01\",\"2025-09-01\"]}")
    private Map<String, Object> filters;

    @Schema(description = "Show soft-deleted records if true", example = "false")
    @Builder.Default
    private boolean deleted = false;
}
