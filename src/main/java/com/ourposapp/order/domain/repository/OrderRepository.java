package com.ourposapp.order.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ourposapp.order.domain.entity.Order;

public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {

    List<Order> findByUserId(@Param("userId") Long userId);
}
