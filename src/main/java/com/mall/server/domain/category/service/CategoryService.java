package com.mall.server.domain.category.service;

import com.mall.server.domain.category.domain.Category;
import com.mall.server.domain.category.dto.CategoryInfo;
import com.mall.server.domain.category.dto.UpdateCategory;
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
        List<Category> rootCategories;

        if (id != null) {
            rootCategories = categoryRepository.findAllChildren(id, true);
        } else {
            rootCategories = categoryRepository.findRootCategories(true);
        }

        return rootCategories.stream()
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
    public CategoryInfo updateCategory(Long id, UpdateCategory.Request updateCategory) {
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
        categoryRepository.findChildren(id)
                .forEach(category ->category.update(null, category.getName(), false));

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
