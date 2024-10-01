package com.ourposapp.order.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ourposapp.common.model.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "order_type")
@Entity
@Table(name = "orders")
public abstract class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "order_total_price")
    private Integer totalPrice;

    @Column(name = "order_completed_datetime")
    private LocalDateTime completedDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    protected Order(Long userId, Long storeId, List<OrderDetail> orderDetails) {
        this.userId = userId;
        this.storeId = storeId;
        this.totalPrice = calculateTotalPrice();
        for (OrderDetail orderDetail : orderDetails) {
            addOrderDetail(orderDetail);
        }
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.addOrder(this);
    }

    public Integer calculateTotalPrice() {
        return orderDetails.stream()
                .mapToInt(OrderDetail::getPrice)
                .sum();
    }

    public void setCompleteOrderTime(LocalDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }
}
