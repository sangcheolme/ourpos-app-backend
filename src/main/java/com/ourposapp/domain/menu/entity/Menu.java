package com.ourposapp.domain.menu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.ourposapp.domain.common.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "menu_name")
    private String name;

    @Column(name = "menu_price")
    private Integer price;

    @Column(name = "menu_description")
    private String description;

    @Column(name = "menu_picture_url")
    private String pictureUrl;

    @Column(name = "menu_deleted_yn")
    private Boolean deletedYn;

    @Column(name = "menu_deleted_datetime")
    private LocalDateTime deletedDateTime;

    @Builder
    private Menu(Long categoryId, String name, Integer price, String description, String pictureUrl) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.deletedYn = false;
    }

    public void update(Long categoryId, String name, Integer price, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void delete(LocalDateTime deletedDateTime) {
        this.deletedYn = true;
        this.deletedDateTime = deletedDateTime;
    }
}
