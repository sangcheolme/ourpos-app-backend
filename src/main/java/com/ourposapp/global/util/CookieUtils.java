package com.ourposapp.global.util;

import jakarta.servlet.http.Cookie;

public class CookieUtils {

    public static Cookie create(String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
