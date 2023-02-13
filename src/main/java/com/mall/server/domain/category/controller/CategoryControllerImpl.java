package com.mall.server.domain.category.controller;

import com.mall.server.domain.category.dto.CategoryInfo;
import com.mall.server.domain.category.dto.CreateCategory;
import com.mall.server.domain.category.dto.UpdateCategory;
import com.mall.server.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CreateCategory.ResponseList> findCategories(
            @RequestParam(required = false) Long parentId) {
        categoryService.findCategoriesById(parentId);

        return ResponseEntity.ok(
                CreateCategory.ResponseList.from(categoryService.findCategoriesById(parentId))
        );
    }

    @PostMapping
    public ResponseEntity<CategoryInfo> createCategory(
            @Valid @RequestBody CreateCategory.Request request) {
        CategoryInfo category = categoryService.createCategory(request.getName(), request.getParentId());

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).body(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryInfo> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody UpdateCategory.Request request) {
        CategoryInfo category = categoryService.updateCategory(categoryId, request);

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).body(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).build();
    }

}
