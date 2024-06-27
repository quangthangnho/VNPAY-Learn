package com.thanhquang.sourcebase.exceptions.error_code.impl;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.Getter;

@Getter
public enum CommonErrors implements ErrorCode {
    REQUEST_VALIDATION_FAIL("Common-001", "Request validation failed"),
    ;

    private final String code;
    private final String message;

    CommonErrors(String code, String message) {

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
