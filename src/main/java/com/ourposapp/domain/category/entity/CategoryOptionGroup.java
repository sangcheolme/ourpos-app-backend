package com.ourposapp.domain.category.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category_option_group")
public class CategoryOptionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_option_group_id")
	private Long id;

	@JoinColumn(name = "category_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	@Column(name = "category_option_group_name")
	private String name;

	@Column(name = "category_option_group_exclusive_yn")
	private Boolean exclusiveYn;

	@Column(name = "category_option_group_description")
	private String description;

	@Column(name = "category_option_group_deleted_yn")
	private Boolean deletedYn;

	@Column(name = "category_option_group_deleted_datetime")
	private LocalDateTime deletedDateTime;

	@OneToMany(mappedBy = "categoryOptionGroup", cascade = CascadeType.ALL)
	@SQLRestriction("category_option_deleted_yn = false")
	private List<CategoryOption> categoryOptions = new ArrayList<>();

	@Builder
	private CategoryOptionGroup(String name, Category category, Boolean exclusiveYn, String description, List<CategoryOption> categoryOptions) {
		this.category = category;
		this.name = name;
		this.exclusiveYn = exclusiveYn;
		this.description = description;
		this.deletedYn = false;
		for (CategoryOption categoryOption : categoryOptions) {
			addCategoryOption(categoryOption);
		}
	}

	public void addCategoryOption(CategoryOption categoryOption) {
		categoryOptions.add(categoryOption);
		categoryOption.addCategoryOptionGroup(this);
	}

	void addCategory(Category category) {
		this.category = category;
	}

	public void update(Category category, String name, Boolean exclusiveYn, String description) {
		this.category = category;
		this.name = name;
		this.exclusiveYn = exclusiveYn;
		this.description = description;
	}

	public void delete(LocalDateTime deletedDateTime) {
		this.deletedYn = true;
		this.deletedDateTime = deletedDateTime;
		categoryOptions.forEach(categoryOption -> categoryOption.delete(deletedDateTime));
	}
}
