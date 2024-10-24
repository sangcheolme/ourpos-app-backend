package com.ourposapp.api.service.order;

import java.util.List;

import com.ourposapp.domain.order.Order;

public interface OrderService {

    void save(Order order);

    List<Order> findOrdersByUserId(Long userId);
}
