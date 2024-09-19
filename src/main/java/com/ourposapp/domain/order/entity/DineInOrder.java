package com.ourposapp.domain.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.ourposapp.domain.order.constnat.DineInStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("dine_in")
@Entity
@Table(name = "dine_in_order")
public class DineInOrder extends Order {

    @Column(name = "dine_in_order_status")
    @Enumerated(EnumType.STRING)
    private DineInStatus status;

    @Column(name = "dine_in_order_takeout_yn")
    private Boolean orderTakeoutYn;

    @Builder
    private DineInOrder(Long userId, Long storeId, Boolean orderTakeoutYn, List<OrderDetail> orderDetails) {
        super(userId, storeId, orderDetails);
        this.status = DineInStatus.PAYMENT_PENDING;
        this.orderTakeoutYn = orderTakeoutYn;
    }

    public void cancelOrder() {
        if (this.status != DineInStatus.WAITING) {
            throw new IllegalArgumentException("대기중인 주문만 취소할 수 있습니다.");
        }
        this.status = DineInStatus.CANCELED;
    }

    public void acceptOrder() {
        if (this.status != DineInStatus.WAITING) {
            throw new IllegalArgumentException("대기중인 주문만 접수할 수 있습니다.");
        }
        this.status = DineInStatus.COOKING;
    }

    public void completeOrder(LocalDateTime completeOrderTime) {
        if (this.status != DineInStatus.COOKING) {
            throw new IllegalArgumentException("조리중인 주문만 완료할 수 있습니다.");
        }
        this.status = DineInStatus.COMPLETED;
        super.setCompleteOrderTime(completeOrderTime);
    }

}
