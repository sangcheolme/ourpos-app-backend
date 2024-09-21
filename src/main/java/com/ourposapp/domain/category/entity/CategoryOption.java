package com.ourposapp.domain.category.entity;

import java.time.LocalDateTime;

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
@Table(name = "category_option")
public class CategoryOption {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_option_id")
	private Long id;

	@Setter
	@JoinColumn(name = "category_option_group_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private CategoryOptionGroup categoryOptionGroup;

	@Column(name = "category_option_name")
	private String name;

	@Column(name = "category_option_price")
	private Integer price;

	@Column(name = "category_option_deleted_yn")
	private Boolean deletedYn;

	@Column(name = "category_option_deleted_datetime")
	private LocalDateTime deletedDateTime;

	@Builder
	private CategoryOption(String name, Integer price, CategoryOptionGroup categoryOptionGroup) {
		this.categoryOptionGroup = categoryOptionGroup;
		this.name = name;
		this.price = price;
		this.deletedYn = false;
	}

	void addCategoryOptionGroup(CategoryOptionGroup categoryOptionGroup) {
		this.categoryOptionGroup = categoryOptionGroup;
	}

	public void update(CategoryOptionGroup categoryOptionGroup, String name, Integer price) {
		this.categoryOptionGroup = categoryOptionGroup;
		this.name = name;
		this.price = price;
	}

	public void delete(LocalDateTime deletedDateTime) {
		this.deletedYn = true;
		this.deletedDateTime = deletedDateTime;
	}
}
