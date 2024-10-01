package com.ourposapp.order.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.ourposapp.order.domain.entity.Order;
import com.ourposapp.order.domain.repository.OrderRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository<Order> orderRepository;

    @Transactional
    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
