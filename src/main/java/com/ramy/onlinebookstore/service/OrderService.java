package com.ramy.onlinebookstore.service;

import com.ramy.onlinebookstore.dto.request.order.CreateOrderRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.order.OrderResponse;
import com.ramy.onlinebookstore.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse create(Long userId, CreateOrderRequest request);

    OrderResponse pay(Long userId, Long orderId);

    Page<Order> getAll(Long userId, PaginationRequest request);

    OrderResponse getOne(Long userId, Long orderId);
}
