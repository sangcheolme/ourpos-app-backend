package com.ourposapp.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.domain.menu.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
