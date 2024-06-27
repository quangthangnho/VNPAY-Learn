package com.thanhquang.sourcebase.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResponse<T> extends ApiResponse<T> {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    private static final int DEFAULT_VALUE = 0;

    public ApiPageResponse(T data) {
        super(data);
    }

    public ApiPageResponse(T data, int page, int size, long totalElements, int totalPages) {
        super(data);
        this.page = data == null ? page : DEFAULT_VALUE;
        this.size = data == null ? size : DEFAULT_VALUE;
        this.totalElements = data == null ? DEFAULT_VALUE : totalElements;
        this.totalPages = data == null ? DEFAULT_VALUE : totalPages;
    }

    public static <T> ApiPageResponse<T> success(T data, int page, int size, long totalElements, int totalPages) {
        return new ApiPageResponse<>(data, page, size, totalElements, totalPages);
    }
}
