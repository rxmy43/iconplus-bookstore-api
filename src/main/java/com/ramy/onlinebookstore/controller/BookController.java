package com.ramy.onlinebookstore.controller;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.dto.request.book.CreateBookRequest;
import com.ramy.onlinebookstore.dto.request.book.UpdateBookRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.book.BookResponse;
import com.ramy.onlinebookstore.entity.Book;
import com.ramy.onlinebookstore.service.BookService;
import com.ramy.onlinebookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping()
    @ResponseSuccessMessage("New book has been created!")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse create(@ModelAttribute CreateBookRequest request) {
        return bookService.create(request);
    }

    @GetMapping
    @ResponseSuccessMessage("List of books has been fetched!")
    public Page<Book> getAll(@RequestParam Map<String, String> queryParamas) {
        PaginationRequest request = PaginationUtil.parseQueryParams(queryParamas);
        return bookService.getAll(request);
    }

    @GetMapping("/{id}")
    @ResponseSuccessMessage("Book has been fetched!")
    public BookResponse getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @PutMapping("/{id}")
    @ResponseSuccessMessage("Book has been updated!")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse update(@PathVariable Long id, @ModelAttribute UpdateBookRequest request) {
        return bookService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseSuccessMessage("Book has been removed!")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse remove(@PathVariable Long id) {
        return bookService.remove(id);
    }

    @PostMapping("/{id}/restore")
    @ResponseSuccessMessage("Book has been restored!")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse restore(@PathVariable Long id) {
        return bookService.restore(id);
    }

    @DeleteMapping("/{id}/hard")
    @ResponseSuccessMessage("Book has been permanently removed!")
    @PreAuthorize("hasRole('ADMIN')")
    public BookResponse hardRemove(@PathVariable Long id) {
        return bookService.hardRemove(id);
    }
}
