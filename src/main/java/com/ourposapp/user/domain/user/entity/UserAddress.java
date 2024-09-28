package com.ourposapp.user.domain.user.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ourposapp.common.model.Address;
import com.ourposapp.common.model.BaseTimeEntity;
import com.ourposapp.common.model.Phone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_address")
@Entity
public class UserAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_address_id")
    private Long id;

    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(name = "user_address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "user_address2")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "user_zipcode"))
    })
    private Address address;

    @Column(name = "user_address_name")
    private String name;

    @Column(name = "user_address_receiver_name")
    private String receiverName;

    @Embedded
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "user_address_phone"))
    private Phone phone;

    @Column(name = "user_address_default_yn")
    private Boolean defaultYn;

    @Builder
    public UserAddress(User user, Address address, String name, String receiverName, Phone phone) {
        this.user = user;
        this.address = address;
        this.name = name;
        this.receiverName = receiverName;
        this.phone = phone;
        this.defaultYn = false;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void setAsDefault() {
        this.defaultYn = true;
    }

    public void unsetDefault() {
        this.defaultYn = false;
    }

    public void update(String name, String receiverName, Phone phone, Address address) {
        this.name = name;
        this.receiverName = receiverName;
        this.phone = phone;
        this.address = address;
    }

}
