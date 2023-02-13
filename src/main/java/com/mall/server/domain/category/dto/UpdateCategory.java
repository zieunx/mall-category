package com.mall.server.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
public class UpdateCategory {
    private Long parentId;
    private Boolean displayYn;

    @Getter
    public static class Request {
        @Min(value = 0)
        private Long parentId;
        private String name;
        private Boolean displayYn;
    }
}
