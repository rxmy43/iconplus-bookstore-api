package com.ramy.onlinebookstore.service;

import com.ramy.onlinebookstore.dto.request.book.CreateBookRequest;
import com.ramy.onlinebookstore.dto.request.book.UpdateBookRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.book.BookResponse;
import com.ramy.onlinebookstore.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    BookResponse create(CreateBookRequest request);

    Page<Book> getAll(PaginationRequest request);

    BookResponse getOne(Long id);

    BookResponse update(Long id, UpdateBookRequest request);

    BookResponse remove(Long id);

    BookResponse restore(Long id);

    BookResponse hardRemove(Long id);
}
