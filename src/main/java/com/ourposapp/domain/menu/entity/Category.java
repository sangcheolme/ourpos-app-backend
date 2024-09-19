package com.ourposapp.domain.menu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.SQLRestriction;

import com.ourposapp.domain.common.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;

	@Column(name = "category_name")
	private String name;

	@Column(name = "category_deleted_yn")
	private Boolean deletedYn;

	@Column(name = "category_deleted_date_time")
	private LocalDateTime deletedDateTime;

	@OneToMany(mappedBy = "category")
	@SQLRestriction("menu_option_group_deleted_yn = false")
	private List<MenuOptionGroup> menuOptionGroups = new ArrayList<>();

	@Builder
	private Category(String name, @Singular List<MenuOptionGroup> menuOptionGroups) {
		this.name = name;
		this.deletedYn = false;
		for (MenuOptionGroup menuOptionGroup : menuOptionGroups) {
			addMenuOptionGroup(menuOptionGroup);
		}
	}

	// 연관관계 편의 메서드
	public void addMenuOptionGroup(MenuOptionGroup menuOptionGroup) {
		menuOptionGroups.add(menuOptionGroup);
		menuOptionGroup.addCategory(this);
	}

	public void update(String name) {
		this.name = name;
	}

	public void delete(LocalDateTime deletedDateTime) {
		this.deletedYn = true;
		this.deletedDateTime = deletedDateTime;
		menuOptionGroups.forEach(menuOptionGroup -> menuOptionGroup.delete(deletedDateTime));
	}
}

