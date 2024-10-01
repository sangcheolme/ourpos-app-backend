package com.ourposapp.category.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.SQLRestriction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ourposapp.common.model.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_deleted_yn")
    private Boolean deletedYn;

    @Column(name = "category_deleted_date_time")
    private LocalDateTime deletedDateTime;

    @OneToMany(mappedBy = "category")
    @SQLRestriction("category_option_group_deleted_yn = false")
    private List<CategoryOptionGroup> categoryOptionGroups = new ArrayList<>();

    @Builder
    private Category(String name, List<CategoryOptionGroup> categoryOptionGroups) {
        this.name = name;
        this.deletedYn = false;
        for (CategoryOptionGroup categoryOptionGroup : categoryOptionGroups) {
            addMenuOptionGroup(categoryOptionGroup);
        }
    }

    public void addMenuOptionGroup(CategoryOptionGroup categoryOptionGroup) {
        categoryOptionGroups.add(categoryOptionGroup);
        categoryOptionGroup.addCategory(this);
    }

    public void update(String name) {
        this.name = name;
    }

    public void delete(LocalDateTime deletedDateTime) {
        this.deletedYn = true;
        this.deletedDateTime = deletedDateTime;
        categoryOptionGroups.forEach(categoryOptionGroup -> categoryOptionGroup.delete(deletedDateTime));
    }
}

