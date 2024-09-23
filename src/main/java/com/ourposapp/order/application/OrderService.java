package com.ourposapp.order.application;

import java.util.List;

import com.ourposapp.order.domain.entity.Order;

public interface OrderService {

    void save(Order order);

    List<Order> findOrdersByUserId(Long userId);
}
