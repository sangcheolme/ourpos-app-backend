package com.ourposapp.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ourposapp.domain.generic.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customer_address")
@Entity
public class CustomerAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_address_id")
    private Long id;

    @JoinColumn(name = "customer_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "customer_address_name")
    private String name;

    @Column(name = "customer_address_receiver_name")
    private String receiverName;

    @Column(name = "customer_address_tel_no")
    private String telNo;

    @Column(name = "customer_address_base")
    private String base;

    @Column(name = "customer_address_detail")
    private String detail;

    @Column(name = "customer_address_zipcode")
    private String zipcode;

    @Column(name = "customer_address_default_yn")
    private Boolean defaultYn;

    @Builder
    public CustomerAddress(String name, String receiverName, String telNo, String base, String detail, String zipcode) {
        this.name = name;
        this.receiverName = receiverName;
        this.telNo = telNo;
        this.base = base;
        this.detail = detail;
        this.zipcode = zipcode;
        this.defaultYn = false;
    }

    public void addCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAsDefault() {
        this.defaultYn = true;
    }

    public void update(String name, String receiverName, String telNo, String base, String detail, String zipcode) {
        this.name = name;
        this.receiverName = receiverName;
        this.telNo = telNo;
        this.base = base;
        this.detail = detail;
        this.zipcode = zipcode;
    }

}
