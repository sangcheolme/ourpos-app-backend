package com.ourposapp.domain.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository<T extends Order> extends JpaRepository<T, Long> {

    List<Order> findByUserId(@Param("userId") Long userId);
}
