package com.ourposapp.domain.order.service;

import java.util.List;

import com.ourposapp.domain.order.entity.Order;

public interface OrderService {

    void save(Order order);

    List<Order> findOrdersByUserId(Long userId);
}
