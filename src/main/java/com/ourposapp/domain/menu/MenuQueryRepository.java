package com.ourposapp.domain.menu;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ourposapp.api.controller.menu.request.MenuSearchRequest;

public interface MenuQueryRepository {

    List<Menu> findMenus(MenuSearchRequest menuSearchRequest, Pageable pageable);
}

