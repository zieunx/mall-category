package com.mall.server.domain.category.dto.response;

import com.mall.server.domain.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CategoryInfo {
    private Long id;
    private String name;
    private Boolean displayYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private CategoryInfo ancestors;
    private List<CategoryInfo> children;

    @Builder
    public CategoryInfo(
            Long id,
            String name,
            Boolean displayYn,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            List<CategoryInfo> children
    ) {
        this.id = id;
        this.name = name;
        this.displayYn = displayYn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.children = children;
    }

    public static CategoryInfo from(Category category) {
        return CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .displayYn(category.getDisplayYn())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .deletedAt(category.getDeletedAt())
                .build();
    }

    public static CategoryInfo create(Category category, List<CategoryInfo> children) {
        return CategoryInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .displayYn(category.getDisplayYn())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .deletedAt(category.getDeletedAt())
                .children(children)
                .build();
    }
}
