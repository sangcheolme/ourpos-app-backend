package com.ourposapp.api.controller.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.controller.menu.response.MenuResponse;
import com.ourposapp.api.service.menu.MenuService;
import com.ourposapp.support.response.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public Result<Page<MenuResponse>> findMenus(Pageable pageable) {
        Page<MenuResponse> menuResponses = menuService.findMenus(pageable);
        return Result.of(menuResponses, "전체 메뉴 페이징 조회");
    }
}
