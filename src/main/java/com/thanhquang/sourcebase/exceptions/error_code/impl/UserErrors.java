package com.thanhquang.sourcebase.exceptions.error_code.impl;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrors implements ErrorCode {
    EMAIL_EXISTS("User-001", "User with this email already exists"),
    PHONE_EXISTS("User-002", "User with this phone already exists"),
    USER_DEACTIVATED("User-003", "User is deactivated"),
    ;

    private final String code;
    private final String message;

    UserErrors(String code, String message) {

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
