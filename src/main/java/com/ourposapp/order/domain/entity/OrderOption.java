package com.ourposapp.order.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_option")
public class OrderOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_option_id")
    private Long id;

    @Setter
    @JoinColumn(name = "order_option_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderOptionGroup orderOptionGroup;

    @Column(name = "order_option_name")
    private String name;

    @Column(name = "order_option_price")
    private Integer price;

    @Builder
    private OrderOption(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
