package com.ourposapp.category.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.ourposapp.common.model.Money;
import com.ourposapp.global.converter.MoneyConverter;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category_option")
public class CategoryOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_option_id")
	private Long id;

	@JoinColumn(name = "category_option_group_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private CategoryOptionGroup categoryOptionGroup;

	@Column(name = "category_option_name")
	private String name;

	@Convert(converter = MoneyConverter.class)
	@Column(name = "category_option_price")
	private Money price;

	@Column(name = "category_option_deleted_yn")
	private Boolean deletedYn;

	@Column(name = "category_option_deleted_datetime")
	private LocalDateTime deletedDateTime;

	@Builder
	private CategoryOption(CategoryOptionGroup categoryOptionGroup, String name, Money price) {
		this.name = name;
		this.price = price;
		this.categoryOptionGroup = categoryOptionGroup;
		this.deletedYn = false;
	}

	void addCategoryOptionGroup(CategoryOptionGroup categoryOptionGroup) {
		this.categoryOptionGroup = categoryOptionGroup;
	}

	public void update(CategoryOptionGroup categoryOptionGroup, String name, Money price) {
		this.categoryOptionGroup = categoryOptionGroup;
		this.name = name;
		this.price = price;
	}

	public void delete(LocalDateTime deletedDateTime) {
		this.deletedYn = true;
		this.deletedDateTime = deletedDateTime;
	}
}
