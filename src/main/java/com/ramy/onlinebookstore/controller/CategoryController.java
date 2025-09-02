package com.ramy.onlinebookstore.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ramy.onlinebookstore.annotation.ResponseSuccessMessage;
import com.ramy.onlinebookstore.constant.MediaType;
import com.ramy.onlinebookstore.dto.request.category.CreateCategoryRequest;
import com.ramy.onlinebookstore.dto.request.pagination.PaginationRequest;
import com.ramy.onlinebookstore.dto.response.category.CategoryResponse;
import com.ramy.onlinebookstore.entity.Category;
import com.ramy.onlinebookstore.service.CategoryService;
import com.ramy.onlinebookstore.util.PaginationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create Category", description = "Create book's category, only admin allowed")
    @ApiResponse(responseCode = "201", description = "New category has been created!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CategoryResponse.class)))
    @PostMapping()
    @ResponseSuccessMessage("New book's category has been created!")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @Operation(summary = "Find all Categories", description = "Find all, searching, filtering, and ordering category records")
    @ApiResponse(responseCode = "200", description = "Category records found!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Page.class)))
    @GetMapping()
    @ResponseSuccessMessage("List of category has been fetched successfully!")
    public Page<Category> getAll(@RequestParam Map<String, String> queryParams) {
        PaginationRequest request = PaginationUtil.parseQueryParams(queryParams);
        return categoryService.getAll(request);
    }
}
