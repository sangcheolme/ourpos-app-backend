package com.ourposapp.domain.order.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.ourposapp.domain.order.constnat.DeliveryStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("delivery")
@Entity
@Table(name = "delivery_order")
public class DeliveryOrder extends Order {

    @JoinColumn(name = "order_address_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private DeliveryAddress deliveryAddress;

    @Column(name = "delivery_order_owner_message")
    private String ownerMessage;

    @Column(name = "delivery_order_rider_message")
    private String riderMessage;

    @Column(name = "delivery_order_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(name = "delivery_order_tip")
    private Integer tip;

    @Column(name = "delivery_order_disposable_yn")
    private Boolean disposableYn;

    @Column(name = "delivery_order_estimated_time")
    private LocalTime estimatedTime;

    @Builder
    private DeliveryOrder(Long userId, Long storeId, DeliveryAddress deliveryAddress, String ownerMessage,
        String riderMessage, Integer tip, Boolean disposableYn, List<OrderDetail> orderDetails) {
        super(userId, storeId, orderDetails);
        this.deliveryAddress = deliveryAddress;
        this.ownerMessage = ownerMessage;
        this.riderMessage = riderMessage;
        this.tip = tip;
        this.disposableYn = disposableYn;
        this.status = DeliveryStatus.PAYMENT_PENDING;
    }

    public void cancelOrder() {
        if (isCancelable()) {
            throw new IllegalArgumentException("대기중인 주문만 취소할 수 있습니다.");
        }
        this.status = DeliveryStatus.CANCELED;
        // TODO: 결제 취소 로직
    }

    public void acceptOrder() {
        if (isCancelable()) {
            throw new IllegalArgumentException("대기중인 주문만 접수할 수 있습니다.");
        }
        this.status = DeliveryStatus.COOKING;
    }

    public void startDelivery() {
        if (this.status != DeliveryStatus.COOKING) {
            throw new IllegalArgumentException("조리중인 주문만 배달을 시작할 수 있습니다.");
        }
        this.status = DeliveryStatus.DELIVERING;
    }

    public void completeOrder(LocalDateTime completeOrderTime) {
        if (this.status != DeliveryStatus.DELIVERING) {
            throw new IllegalArgumentException("배달중인 주문만 완료할 수 있습니다.");
        }
        this.status = DeliveryStatus.COMPLETED;
        super.setCompleteOrderTime(completeOrderTime);
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        if (this.status != DeliveryStatus.WAITING && this.status != DeliveryStatus.COOKING) {
            throw new IllegalArgumentException("대기중 또는 조리중인 주문만 배달 예상 시간을 설정할 수 있습니다.");
        }
        this.estimatedTime = estimatedTime;
    }

    private boolean isCancelable() {
        return this.status != DeliveryStatus.PAYMENT_PENDING
            && this.status != DeliveryStatus.WAITING;
    }
}
