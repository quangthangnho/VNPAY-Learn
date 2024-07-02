package com.thanhquang.sourcebase.exceptions.error_code.impl;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.Getter;

@Getter
public enum OrderErrors implements ErrorCode {
    ORDER_ITEMS_EMPTY("Order-001", "Order items is required"),
    ORDER_NOT_FOUND("Order-002", "Order not found"),
    ;

    private final String code;
    private final String message;

    OrderErrors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
