package com.ourposapp.infra.query.menu;


import static com.ourposapp.domain.menu.QMenu.*;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ourposapp.api.controller.menu.request.MenuSearchRequest;
import com.ourposapp.domain.menu.Menu;
import com.ourposapp.domain.menu.MenuQueryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuQueryRepositoryImpl implements MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findMenus(MenuSearchRequest menuSearchRequest, Pageable pageable) {
        return queryFactory
                .select(menu)
                .from(menu)
                .where(
                        menuCategoryEq(menuSearchRequest.getCategoryId()),
                        menuNameEq(menuSearchRequest.getName())
                )
                .fetch();
    }

    private BooleanExpression menuNameEq(String menuName) {
        return menuName != null ? menu.name.eq(menuName) : null;
    }

    private BooleanExpression menuCategoryEq(Long categoryId) {
        return categoryId != null ? menu.categoryId.eq(categoryId) : null;
    }
}
