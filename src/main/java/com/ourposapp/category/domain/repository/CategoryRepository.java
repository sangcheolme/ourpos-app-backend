package com.ourposapp.category.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.category.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
