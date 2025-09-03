package com.ramy.onlinebookstore.controller;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.dto.response.report.BestsellerResponse;
import com.ramy.onlinebookstore.dto.response.report.PriceStatsResponse;
import com.ramy.onlinebookstore.dto.response.report.RevenueResponse;
import com.ramy.onlinebookstore.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/sales")
    @ResponseSuccessMessage("Sales report fetched successfully!")
    public RevenueResponse getRevenue(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        return reportService.getRevenue(startDate, endDate);
    }

    @GetMapping("/bestseller")
    @ResponseSuccessMessage("Top 3 bestseller books fetched successfully!")
    public List<BestsellerResponse> getBestseller(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getBestseller(startDate, endDate);
    }

    @GetMapping("/prices")
    @ResponseSuccessMessage("Book's price stats has been fetched!")
    public PriceStatsResponse getPriceStats() {
        return reportService.getPriceStats();
    }
}
