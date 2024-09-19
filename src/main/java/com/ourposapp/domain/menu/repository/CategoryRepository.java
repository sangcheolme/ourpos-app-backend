package com.ourposapp.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.domain.menu.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
