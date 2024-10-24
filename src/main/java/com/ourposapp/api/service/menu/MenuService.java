package com.ourposapp.api.service.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.controller.menu.response.MenuResponse;
import com.ourposapp.domain.menu.Menu;
import com.ourposapp.domain.menu.MenuRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public Page<MenuResponse> findMenus(Pageable pageable) {
        Page<Menu> page = menuRepository.findAll(pageable);
        return page.map(MenuResponse::new);
    }
}
