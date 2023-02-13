package com.mall.server.domain.category.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateCategory {

    @Getter
    public static class Request {
        @NotEmpty
        private String name;

        @Min(value = 1)
        private Long parentId;
    }

    @Getter
    public static class ResponseList {
        private List<CategoryInfo> list;

        private ResponseList(List<CategoryInfo> list) {
            this.list = list;
        }

        public static ResponseList from(List<CategoryInfo> categoryInfos) {
            return new ResponseList(categoryInfos);
        }
    }

}
