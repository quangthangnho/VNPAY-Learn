package com.thanhquang.sourcebase.exceptions.config;

import static com.thanhquang.sourcebase.exceptions.error_code.impl.CommonErrors.REQUEST_VALIDATION_FAIL;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thanhquang.sourcebase.dto.response.common.ApiErrorResponse;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return getMapApiErrorResponse(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiErrorResponse<Map<String, String>> handleBindExceptions(BindException ex) {
        return getMapApiErrorResponse(ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ApiErrorResponse<?> handleAuthenticationException(AuthenticationException ex) {
        return ApiErrorResponse.fail(HttpStatus.UNAUTHORIZED.value() + "", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiErrorResponse<?> handleAccessDeniedException(AccessDeniedException ex) {
        return ApiErrorResponse.fail(HttpStatus.FORBIDDEN.value() + "", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiErrorResponse<String> handleBadRequestException(BadRequestException ex) {
        return ApiErrorResponse.fail(ex.getErrorCode().code(), ex.getErrorCode().message());
    }

    private ApiErrorResponse<Map<String, String>> getMapApiErrorResponse(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiErrorResponse.fail(REQUEST_VALIDATION_FAIL.code(), errors);
    }
}
