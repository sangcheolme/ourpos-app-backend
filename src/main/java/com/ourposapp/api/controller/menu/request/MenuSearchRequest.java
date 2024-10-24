package com.ourposapp.api.controller.menu.request;

import lombok.Getter;

@Getter
public class MenuSearchRequest {

    private Long categoryId;
    private String name;
    private String description;
}
