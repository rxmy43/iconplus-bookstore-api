package com.ramy.onlinebookstore.service.impl;

import com.ramy.onlinebookstore.dto.request.order.CreateOrderRequest;
import com.ramy.onlinebookstore.dto.request.order.OrderItemRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.order.OrderResponse;
import com.ramy.onlinebookstore.entity.Book;
import com.ramy.onlinebookstore.entity.Order;
import com.ramy.onlinebookstore.entity.OrderItem;
import com.ramy.onlinebookstore.entity.User;
import com.ramy.onlinebookstore.entity.enums.OrderStatus;
import com.ramy.onlinebookstore.entity.enums.UserRole;
import com.ramy.onlinebookstore.repository.BookRepository;
import com.ramy.onlinebookstore.repository.OrderItemRepository;
import com.ramy.onlinebookstore.repository.OrderRepository;
import com.ramy.onlinebookstore.repository.UserRepository;
import com.ramy.onlinebookstore.service.OrderService;
import com.ramy.onlinebookstore.specification.OrderSpecification;
import com.ramy.onlinebookstore.util.PaginationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;

    @Override
    @Transactional
    public OrderResponse create(Long userId, CreateOrderRequest request) {
        // User validation
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Book validation
        List<Long> bookIds = request.getOrderItems().stream()
                .map(OrderItemRequest::getBookId)
                .toList();

        List<Book> books = bookRepo.findAllById(bookIds);
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, b -> b));

        // Book ids check
        for (Long id : bookIds) {
            if (!bookMap.containsKey(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            }
        }

        // Create order
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .totalPrice(BigDecimal.ZERO)
                .build();
        orderRepo.save(order);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest orderItemRequest : request.getOrderItems()) {
            Book book = bookMap.get(orderItemRequest.getBookId());
            if (book.getStock() < orderItemRequest.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for book " + book.getTitle() + " , current stock is " +  book.getStock());
            }

            BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()));
            totalPrice = totalPrice.add(price);

            orderItems.add(OrderItem.builder()
                    .order(order)
                    .book(book)
                    .quantity(orderItemRequest.getQuantity())
                    .price(price)
                    .build());

            int newStock = book.getStock() - orderItemRequest.getQuantity();
            book.setStock(newStock);
        }

        bookRepo.saveAll(books);
        orderItemRepo.saveAll(orderItems);

        order.setTotalPrice(totalPrice);
        orderRepo.save(order);
        return OrderResponse.builder()
                .orderId(order.getId())
                .orderItems(order.getOrderItems())
                .totalPrice(totalPrice)
                .status(order.getStatus())
                .buyerName(user.getName())
                .build();
    }

    @Override
    public OrderResponse pay(Long userId, Long orderId) {
        Optional<Order> order = orderRepo.findByIdAndUserId(orderId, userId);
        if (!order.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        if (order.get().getStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pending order not found");
        }

        order.get().setStatus(OrderStatus.PAID);
        orderRepo.save(order.get());
        return OrderResponse.builder()
                .orderId(order.get().getId())
                .totalPrice(order.get().getTotalPrice())
                .status(order.get().getStatus())
                .buyerName(order.get().getUser().getName())
                .build();
    }

    @Override
    public Page<Order> getAll(Long userId, PaginationRequest request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(),
                PaginationUtil.getSort(request.getSortField(), request.getSortDir()));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        OrderSpecification spec = new OrderSpecification(request.getFilters(), request.isDeleted(), user.getId(), user.getRole());
        return orderRepo.findAll(spec, pageable);

    }

    @Override
    public OrderResponse getOne(Long userId, Long orderId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Optional<Order> order;
        if (user.getRole().equals(UserRole.USER)) {
           order =  orderRepo.findByIdAndUserId(orderId, userId);
        } else {
            order = orderRepo.findById(orderId);
        }

        if (!order.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        return OrderResponse.builder()
                .orderId(order.get().getId())
                .orderItems(order.get().getOrderItems())
                .totalPrice(order.get().getTotalPrice())
                .status(order.get().getStatus())
                .buyerName(order.get().getUser().getName())
                .build();
    }
}
