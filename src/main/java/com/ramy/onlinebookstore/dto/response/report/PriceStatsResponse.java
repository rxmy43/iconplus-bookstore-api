package com.ramy.onlinebookstore.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Statistic Book's price")
public class PriceStatsResponse {
    @Schema(description = "Highest book's price")
    private BigDecimal maxPrice;

    @Schema(description = "Lowest book's price")
    private BigDecimal minPrice;

    @Schema(description = "Average book's price")
    private BigDecimal averagePrice;
}
