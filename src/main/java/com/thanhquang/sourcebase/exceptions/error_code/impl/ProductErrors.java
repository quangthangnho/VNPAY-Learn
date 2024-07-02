package com.thanhquang.sourcebase.exceptions.error_code.impl;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.Getter;

@Getter
public enum ProductErrors implements ErrorCode {
    PRODUCT_NOT_FOUND("Product-001", "Product not found"),
    ;

    private final String code;
    private final String message;

    ProductErrors(String code, String message) {
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
