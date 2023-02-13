package com.mall.server.domain.category.controller;

import com.mall.server.domain.category.dto.CategoryInfo;
import com.mall.server.domain.category.dto.CreateCategory;
import com.mall.server.domain.category.dto.UpdateCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Category", tags = {"Category Controller"})
public interface CategoryController {

    @ApiOperation(value = "카테고리 조회", notes = "카테고리를 조회한다. 부모 카테고리 없이 요청 시 전체가 조회된다.")
    @GetMapping
    ResponseEntity<CreateCategory.ResponseList> findCategories(
            @ApiParam(value = "부모 카테고리 아이디", type = "Long")
            @RequestParam(required = false)
            Long parentId
    );

    @ApiOperation(value = "카테고리 등록", notes = "카테고리를 등록한다. 기본이 <strong>미노출</strong>이기 때문에 등록 후 노출처리가 필요하다.")
    @PostMapping
    ResponseEntity<CategoryInfo> createCategory(
            @ApiParam(value = "카테고리 등록 요청", required = true)
            @Valid @RequestBody CreateCategory.Request request);

    @ApiOperation(value = "카테고리 수정", notes = "카테고리를 수정한다. 계층 이동이 가능하다.")
    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryInfo> updateCategory(
            @ApiParam(value = "대상 카테고리 아이디", type = "Long", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody UpdateCategory.Request request
    );

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리를 삭제한다.")
    @DeleteMapping("/{categoryId}")
    ResponseEntity<?> deleteCategory(
            @ApiParam(value = "대상 카테고리 아이디", type = "Long", required = true)
            @PathVariable Long categoryId
    );

}
