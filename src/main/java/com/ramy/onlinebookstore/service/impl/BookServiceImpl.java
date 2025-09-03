package com.ramy.onlinebookstore.service.impl;

import com.ramy.onlinebookstore.dto.request.book.CreateBookRequest;
import com.ramy.onlinebookstore.dto.request.book.UpdateBookRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.book.BookResponse;
import com.ramy.onlinebookstore.entity.Book;
import com.ramy.onlinebookstore.entity.Category;
import com.ramy.onlinebookstore.repository.BookRepository;
import com.ramy.onlinebookstore.repository.CategoryRepository;
import com.ramy.onlinebookstore.service.BookService;
import com.ramy.onlinebookstore.specification.BookSpecification;
import com.ramy.onlinebookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public BookResponse create(CreateBookRequest request) {
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .stock(request.getStock())
                .year(request.getYear())
                .category(category)
                .imageBase64(convertImageToBase64(request.getImage()))
                .build();

        bookRepo.save(book);
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }

    private String convertImageToBase64(MultipartFile image) {
        try {
            byte[] bytes = image.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Page<Book> getAll(PaginationRequest request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(),
                PaginationUtil.getSort(request.getSortField(), request.getSortDir()));

        BookSpecification spec = new BookSpecification(request.getFilters(), request.isDeleted());
        return bookRepo.findAll(spec, pageable);
    }

    @Override
    public BookResponse getOne(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }

    @Override
    public BookResponse update(Long id, UpdateBookRequest request) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (request.getCategoryId() != null) {
            Category category = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            book.setCategory(category);
        }

        if (request.getImage() != null) {
            book.setImageBase64(convertImageToBase64(request.getImage()));
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        bookRepo.save(book);

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }

    @Override
    public BookResponse remove(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        book.softDelete();
        bookRepo.save(book);
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }

    @Override
    public BookResponse restore(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        if (book.getDeletedAt() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        book.restore();
        bookRepo.save(book);
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }

    @Override
    public BookResponse hardRemove(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        bookRepo.delete(book);
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .price(book.getPrice())
                .stock(book.getStock())
                .category(book.getCategory().getName())
                .imageBase64("https://test.image.com")
                .build();
    }
}
