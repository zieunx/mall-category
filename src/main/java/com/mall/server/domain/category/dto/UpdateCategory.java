package com.mall.server.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateCategory {
    private Long parentId;
    private Boolean displayYn;
}
