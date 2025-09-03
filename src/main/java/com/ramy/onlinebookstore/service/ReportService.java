package com.ramy.onlinebookstore.service;

import com.ramy.onlinebookstore.dto.response.report.BestsellerResponse;
import com.ramy.onlinebookstore.dto.response.report.PriceStatsResponse;
import com.ramy.onlinebookstore.dto.response.report.RevenueResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    RevenueResponse getRevenue(LocalDate startDate, LocalDate endDate);

    List<BestsellerResponse> getBestseller(LocalDate startDate, LocalDate endDate);

    PriceStatsResponse getPriceStats();
}
