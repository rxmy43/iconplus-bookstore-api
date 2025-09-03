package com.ramy.onlinebookstore.controller;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.dto.request.order.CreateOrderRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.order.OrderResponse;
import com.ramy.onlinebookstore.entity.Order;
import com.ramy.onlinebookstore.security.AuthUtil;
import com.ramy.onlinebookstore.service.OrderService;
import com.ramy.onlinebookstore.util.PaginationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final AuthUtil authUtil;

    @PostMapping
    @ResponseSuccessMessage("Order has been created!")
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
        Long userId = authUtil.getUserId();
        return orderService.create(userId, request);
    }

    @PostMapping("/{id}/pay")
    @ResponseSuccessMessage("Payment success, thank you for ordering!")
    public OrderResponse pay(@PathVariable Long id) {
        Long userId = authUtil.getUserId();
        return orderService.pay(userId, id);
    }

    @GetMapping
    @ResponseSuccessMessage("Order has been fetched!")
    public Page<Order> getAll(@RequestParam Map<String, String> queryParams) {
        PaginationRequest request = PaginationUtil.parseQueryParams(queryParams);
        Long userId = authUtil.getUserId();
        return orderService.getAll(userId, request);
    }

    @GetMapping("/{id}")
    @ResponseSuccessMessage("Order has been fetched!")
    public OrderResponse getOne(@PathVariable Long id) {
        Long userId = authUtil.getUserId();
        return orderService.getOne(userId, id);
    }
}
