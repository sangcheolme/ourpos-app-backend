package com.ourposapp.domain.order.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.ourposapp.domain.common.Address;
import com.ourposapp.domain.common.Phone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "delivery_address")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Long id;

    @Column(name = "delivery_address_receiver_name")
    private String receiverName;

    @Column(name = "delivery_address_tel_no")
    private Phone phone;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "address1", column = @Column(name = "delivery_address1")),
        @AttributeOverride(name = "address2", column = @Column(name = "delivery_address2")),
        @AttributeOverride(name = "zipcode", column = @Column(name = "delivery_address_zipcode"))
    })
    private Address address;

    @Builder
    private DeliveryAddress(String receiverName, Phone phone, Address address) {
        this.receiverName = receiverName;
        this.phone = phone;
        this.address = address;
    }
}
