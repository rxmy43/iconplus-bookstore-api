package com.ramy.onlinebookstore.service;

import com.ramy.onlinebookstore.dto.request.category.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

import com.ramy.onlinebookstore.dto.request.category.CreateCategoryRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.category.CategoryResponse;
import com.ramy.onlinebookstore.entity.Category;

public interface CategoryService {
    CategoryResponse create(CreateCategoryRequest request);

    Page<Category> getAll(PaginationRequest request);

    CategoryResponse getOne(Long id);

    CategoryResponse update(Long id, UpdateCategoryRequest request);

    CategoryResponse remove(Long id);

    CategoryResponse hardRemove(Long id);

    CategoryResponse restore(Long id);
}
