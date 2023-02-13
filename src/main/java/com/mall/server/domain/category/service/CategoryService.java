package com.mall.server.domain.category.service;

import com.mall.server.domain.category.domain.Category;
import com.mall.server.domain.category.dto.response.CategoryInfo;
import com.mall.server.domain.category.dto.request.UpdateCategoryRequest;
import com.mall.server.domain.category.exception.CategoryNotFoundException;
import com.mall.server.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryInfo> findCategoriesById(Long id) {
        if (id != null) {
            return findChildrenById(id);
        }
        return findAllCategories();
    }

    @Transactional(readOnly = true)
    public List<CategoryInfo> findAllCategories() {
        List<Category> collect = categoryRepository.findRootCategories(true);
        return collect.stream()
                .map(category -> CategoryInfo.create(category, findChildren(category)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryInfo> findChildrenById(Long id) {
        List<Category> collect = categoryRepository.findAllChildren(id, true);
        return collect.stream()
                .map(category -> CategoryInfo.create(category, findChildren(category)))
                .collect(Collectors.toList());
    }

    private List<CategoryInfo> findChildren(Category category) {
        List<Category> children = categoryRepository.findAllChildren(category.getId(), true);

        if (children.isEmpty()) {
            return new ArrayList<>();
        }

        return children.stream()
                .map(child -> CategoryInfo.create(child, findChildren(child)))
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryInfo createCategory(String name, Long parentId) {
        Category parentCategory = getParentCategory(parentId);

        Category category = categoryRepository.save(Category.create(name, parentCategory));

        return CategoryInfo.from(category);
    }

    @Transactional
    public CategoryInfo updateCategory(Long id, UpdateCategoryRequest updateCategory) {
        Category category = getCategory(id);

        if (updateCategory.getParentId() != null) {
            getCategory(updateCategory.getParentId());
        }

        category.update(
                getParentCategory(updateCategory.getParentId()),
                updateCategory.getName(),
                updateCategory.getDisplayYn()
        );

        return CategoryInfo.from(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findAllChildren(id)
                .forEach(category ->category.update(null, category.getName(), category.getDisplayYn()));

        Category category = getCategory(id);
        category.delete();
    }

    private Category getParentCategory(Long parentId) {
        Category parentCategory = null;
        if (parentId != null) {
            parentCategory = getCategory(parentId);
        }
        return parentCategory;
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.valueOf(id)));
    }
}
