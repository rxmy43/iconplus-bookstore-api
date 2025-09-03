package com.ramy.onlinebookstore.service.impl;

import com.ramy.onlinebookstore.dto.response.report.BestsellerResponse;
import com.ramy.onlinebookstore.dto.response.report.PriceStatsResponse;
import com.ramy.onlinebookstore.dto.response.report.RevenueResponse;
import com.ramy.onlinebookstore.repository.BookRepository;
import com.ramy.onlinebookstore.repository.OrderItemRepository;
import com.ramy.onlinebookstore.repository.OrderRepository;
import com.ramy.onlinebookstore.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final BookRepository bookRepo;

    @Override
    public RevenueResponse getRevenue(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        BigDecimal totalRevenue = orderRepo.sumRevenueBetweenDates(startDateTime, endDateTime);
        Long totalBooksSold = orderRepo.sumBooksSoldBetweenDates(startDateTime, endDateTime);

        return RevenueResponse.builder()
                .totalBooksSold(totalBooksSold)
                .totalRevenue(totalRevenue)
                .build();
    }

    @Override
    public List<BestsellerResponse> getBestseller(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        Pageable top3 = PageRequest.of(0, 3);

        List<Object[]> rows = orderItemRepo.findTopBestsellersRaw(startDateTime, endDateTime, top3);

        return rows.stream().map(r -> {
            String title = (String) r[0];
            String author = (String) r[1];
            int year = (Integer) r[2];
            String category = (String) r[3];
            BigDecimal price = (BigDecimal) r[4];
            Long totalSold = (Long) r[5];

            return BestsellerResponse.builder()
                    .title(title)
                    .author(author)
                    .year(String.valueOf(year))
                    .category(category)
                    .price(price)
                    .totalBookSold(totalSold != null ? totalSold.intValue() : 0)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public PriceStatsResponse getPriceStats() {
        List<Object[]> statsList = bookRepo.findPriceStatistics();
        Object[] stats = statsList.get(0);
        BigDecimal maxPrice = stats[0] != null ? (BigDecimal) stats[0] : BigDecimal.ZERO;
        BigDecimal minPrice = stats[1] != null ? (BigDecimal) stats[1] : BigDecimal.ZERO;
        BigDecimal avgPrice = stats[2] != null ? BigDecimal.valueOf((Double) stats[2]) : BigDecimal.ZERO;
        return PriceStatsResponse.builder()
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .averagePrice(avgPrice)
                .build();
    }
}
