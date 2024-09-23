package com.ourposapp.menu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.menu.domain.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
