package com.mall.server.domain.category.repository;

import com.mall.server.config.JpaConfig;
import com.mall.server.domain.category.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    @DisplayName("생성")
    class Update {
        @Test
        void 기본값_저장() {
            // given
            Category category = Category.create("카테고리명1", null);

            // when
            Category saveCategory = categoryRepository.save(category);

            // then
            assertThat(saveCategory.getId()).isNotNull();
            assertThat(saveCategory.getDeletedAt()).isNull();
            assertThat(saveCategory.getDisplayYn()).isNotNull();
            assertThat(saveCategory.getCreatedAt()).isNotNull();
            assertThat(saveCategory.getUpdatedAt()).isNotNull();
        }

        @Test
        void 부모카테고리_있는경우_저장() {
            // given
            Category parent = Category.create("카테고리명1", null);
            Category category = Category.create("카테고리명2", parent);

            // when
            Category saveCategory = categoryRepository.save(category);

            // then
            assertThat(saveCategory.getParentCategory()).isEqualTo(parent);
        }

        @Test
        void 카테고리_이름_널인경우_예외발생() {
            // given
            Category category = Category.create(null, null);

            // when
            assertThatThrownBy(() -> categoryRepository.save(category))
                    .isInstanceOf(ConstraintViolationException.class);
        }
    }
}