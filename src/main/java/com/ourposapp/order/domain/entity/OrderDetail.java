package com.ourposapp.order.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "order_detail_quantity")
    private Integer quantity;

    @Column(name = "order_detail_price")
    private Integer price;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();

    @Builder
    private OrderDetail(Long menuId, Integer quantity, List<OrderOptionGroup> orderOptionGroups) {
        this.menuId = menuId;
        this.quantity = quantity;
        for (OrderOptionGroup orderOptionGroup : orderOptionGroups) {
            addOrderOptionGroup(orderOptionGroup);
        }
        this.price = calculatePrice();
    }

    public void addOrderOptionGroup(OrderOptionGroup orderOptionGroup) {
        orderOptionGroups.add(orderOptionGroup);
        orderOptionGroup.setOrderDetail(this);
    }

    public void addOrder(Order order) {
        this.order = order;
    }

    private Integer calculatePrice() {
        // TODO: 가격 계산 구현
        // int menuPrice = menu.getPrice();
        // int optionPrice = orderOptionGroups.stream()
        //     .mapToInt(OrderOptionGroup::calculatePrice)
        //     .sum();
        // return (menuPrice + optionPrice) * quantity;
        return 0;
    }
}
