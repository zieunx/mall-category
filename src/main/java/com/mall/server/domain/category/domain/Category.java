package com.mall.server.domain.category.domain;

import com.mall.server.converter.BooleanToYnConverter;
import com.mall.server.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "category")
@Entity
public class Category extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = BooleanToYnConverter.class)
    @Column(name = "display_yn", nullable = false)
    private Boolean displayYn = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory = null;

    @Builder
    public Category(Long id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public static Category create(String name, Category parentCategory) {
        return new Category(name, parentCategory);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(Category parentCategory, String name, Boolean displayYn) {
        if (this.parentCategory != parentCategory) {
            this.parentCategory = parentCategory;
        }
        if (name != null && !Objects.equals(name, this.name)) {
            this.name = name;
        }
        if (displayYn != null) {
            this.displayYn = displayYn;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "displayYn = " + displayYn + ", " +
                "deletedAt = " + deletedAt + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
