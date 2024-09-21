package com.ourposapp.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
