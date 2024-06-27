package com.thanhquang.sourcebase.exceptions.error_code.impl;

import com.thanhquang.sourcebase.exceptions.error_code.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthenticationErrors implements ErrorCode {
    USER_NOT_FOUND("Auth-001", "User not found"),
    DEFAULT_ROLE_NOT_FOUND("Auth-002", "Default role not found"),
    REFRESH_TOKEN_NOT_FOUND("Auth-003", "Refresh token not found"),
    REFRESH_TOKEN_EXPIRED("Auth-004", "Refresh token expired"),
    TOKEN_INVALID("Auth-005", "Token invalid"),
    EMAIL_OR_PASSWORD_INVALID("Auth-006", "Email or password invalid"),
    ;

    private final String code;
    private final String message;

    AuthenticationErrors(String code, String message) {

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
