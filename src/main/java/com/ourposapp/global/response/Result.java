package com.ourposapp.global.response;

import lombok.Getter;

@Getter
public class Result<T> {

    private static final int SINGLE_DATA_COUNT = 1;
    private final T data;
    private final String message;
    private final int count;

    private Result(T data, String message, int count) {
        this.data = data;
        this.message = message;
        this.count = count;
    }

    public static <T> Result<T> of(T data, String message, int count) {
        return new Result<>(data, message, count);
    }

    public static <T> Result<T> of(T data, String message) {
        return of(data, message, SINGLE_DATA_COUNT);
    }

    public static Result<Void> of(String message) {
        return new Result<>(null, message, 0);
    }
}

