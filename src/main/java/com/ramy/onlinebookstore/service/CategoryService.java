package com.ramy.onlinebookstore.service;

import org.springframework.data.domain.Page;

import com.ramy.onlinebookstore.dto.request.category.CreateCategoryRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.category.CategoryResponse;
import com.ramy.onlinebookstore.entity.Category;

public interface CategoryService {
    CategoryResponse create(CreateCategoryRequest request);

    Page<Category> getAll(PaginationRequest request);
}
