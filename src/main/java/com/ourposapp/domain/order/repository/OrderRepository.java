package com.ourposapp.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourposapp.domain.order.entity.Order;

public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {
}
