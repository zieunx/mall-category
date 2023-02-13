package com.mall.server.domain.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateCategory {

    @ApiModel(description = "카테고리 등록")
    @Getter
    public static class Request {
        @ApiModelProperty(value = "카테고리 이름", required = true)
        @NotEmpty
        private String name;

        @ApiModelProperty(value = "부모 카테고리 아이디", dataType = "Long", notes = "최소 1 부터 가능")
        @Min(value = 1)
        private Long parentId;
    }

    @ApiModel(description = "카테고리 목록")
    @Getter
    public static class ResponseList {

        @ApiModelProperty(value = "목록", dataType = "List<CategoryInfo>")
        private List<CategoryInfo> list;

        private ResponseList(List<CategoryInfo> list) {
            this.list = list;
        }

        public static ResponseList from(List<CategoryInfo> categoryInfos) {
            return new ResponseList(categoryInfos);
        }
    }

}
