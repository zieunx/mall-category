package com.mall.server.domain.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("삭제")
    void delete() {
        // given
        Category category = Category.create("카테고리명", null);

        // when
        category.delete();

        // then
        assertThat(category.getDeletedAt()).isNotNull();
    }

    @Nested
    @DisplayName("수정")
    class Update {

        Category PARENT_CATEGORY = Category.builder()
                .id(2L)
                .build();
        String CATEGORY_NAME = "카테고리";
        Boolean DISPLAY_YN = true;

        Category category = Category.builder()
                .parentCategory(PARENT_CATEGORY)
                .name(CATEGORY_NAME)
                .displayYn(DISPLAY_YN)
                .build();

        @Nested
        @DisplayName("입력이_널인경우")
        class Null {

            @Test
            void parentCategory는_루트_카테고리가_된다(){
                // when
                category.update(null, CATEGORY_NAME, DISPLAY_YN);

                // then
                assertThat(category.isRoot()).isTrue();
            }

            @Test
            void name은_업데이트되지_않는다(){
                // when
                category.update(PARENT_CATEGORY, null, DISPLAY_YN);

                // then
                assertThat(category.getName()).isEqualTo(CATEGORY_NAME);
            }

            @Test
            void displayYn은_업데이트되지_않는다(){
                // when
                category.update(PARENT_CATEGORY, CATEGORY_NAME, null);

                // then
                assertThat(category.getDisplayYn()).isEqualTo(DISPLAY_YN);
            }
        }

        @Nested
        @DisplayName("입력이_있는경우")
        class NotEmpty {
            @Test
            void 입력값으로_업데이트된다(){
                // given
                Category updateParent = Category.builder()
                        .id(2L)
                        .build();
                String updateName = "카테고리이름변경";
                Boolean updateDisplayYn = !DISPLAY_YN;

                // when
                category.update(updateParent, updateName, updateDisplayYn);

                // then
                assertThat(category.getParentCategory()).isEqualTo(updateParent);
                assertThat(category.getName()).isEqualTo(updateName);
                assertThat(category.getDisplayYn()).isEqualTo(updateDisplayYn);
            }
        }
    }
}