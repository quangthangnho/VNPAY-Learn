package com.thanhquang.sourcebase.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<T> extends ApiResponse<T> {

    private String errorCode;
    private T errorMessage;

    public ApiErrorResponse(String errorCode, T errorMessage) {
        super();
        super.setSuccess(false);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiErrorResponse<T> fail(String errorCode, T errorMessage) {
        return new ApiErrorResponse<>(errorCode, errorMessage);
    }
}
