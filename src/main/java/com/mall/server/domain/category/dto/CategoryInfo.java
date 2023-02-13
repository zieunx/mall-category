package com.mall.server.domain.category.dto;

import com.mall.server.domain.category.domain.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "카테고리")
@Getter
public class CategoryInfo {
    @ApiModelProperty(value = "카테고리 아이디", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "카테고리 이름", dataType = "String")
    private String name;

    @ApiModelProperty(value = "노출 여부", dataType = "Boolean")
    private Boolean displayYn;

    @ApiModelProperty(value = "생성일자", dataType = "LocalDateTime")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일자", dataType = "LocalDateTime")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "삭제일자", dataType = "LocalDateTime")
    private LocalDateTime deletedAt;

    @ApiModelProperty(value = "자식 카테고리", dataType = "List<CategoryInfo>")
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
