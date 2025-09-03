package com.ramy.onlinebookstore.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Revenue Response")
public class RevenueResponse {
    @Schema(description = "Total Revenue based between time period")
    private BigDecimal totalRevenue;

    @Schema(description = "Total book sold between time period")
    private Long totalBooksSold;
}
