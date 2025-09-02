package com.ramy.onlinebookstore.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ramy.onlinebookstore.dto.request.category.CreateCategoryRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.category.CategoryResponse;
import com.ramy.onlinebookstore.entity.Category;
import com.ramy.onlinebookstore.repository.CategoryRepository;
import com.ramy.onlinebookstore.service.CategoryService;
import com.ramy.onlinebookstore.specification.EntitySpecification;
import com.ramy.onlinebookstore.util.PaginationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();

        categoryRepo.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public Page<Category> getAll(PaginationRequest request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(),
                PaginationUtil.getSort(request.getSortField(), request.getSortDir()));

        EntitySpecification<Category> spec = new EntitySpecification<>(request.getFilters());
        return categoryRepo.findAll(spec, pageable);
    }
}
