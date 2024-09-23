package com.ourposapp.order.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DineInStatus {
    PAYMENT_PENDING("결제 전"),
    WAITING("대기 중"),
    COOKING("조리 중"),
    COMPLETED("주문 완료"),
    CANCELED("주문 취소");

    private final String text;
}
