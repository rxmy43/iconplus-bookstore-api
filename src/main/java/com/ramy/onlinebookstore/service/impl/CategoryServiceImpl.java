package com.ramy.onlinebookstore.service.impl;

import com.ramy.onlinebookstore.dto.request.category.UpdateCategoryRequest;
import com.ramy.onlinebookstore.specification.CategorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        CategorySpecification spec = new CategorySpecification(request.getFilters(), request.isDeleted());
        return categoryRepo.findAll(spec, pageable);
    }

    @Override
    public CategoryResponse getOne(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return CategoryResponse.builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
    }

    @Override
    public CategoryResponse update(Long id, UpdateCategoryRequest request) {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Category not found");
        }

        category.get().setName(request.getName());
        categoryRepo.save(category.get());
        return CategoryResponse.builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
    }

    @Override
    public CategoryResponse remove(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Category not found");
        }

        category.get().softDelete();
        categoryRepo.save(category.get());
        return CategoryResponse.builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
    }

    @Override
    public CategoryResponse hardRemove(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Category not found");
        }

        categoryRepo.delete(category.get());
        return CategoryResponse.builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
    }

    @Override
    public CategoryResponse restore(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));

        if (category.getDeletedAt() == null) {
            throw new  ResponseStatusException(HttpStatusCode.valueOf(404), "Category not found");
        }

        category.restore();
        categoryRepo.save(category);
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
