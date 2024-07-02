package com.thanhquang.sourcebase.dto.request.order;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderDto {

    @NotEmpty(message = "orderItemDtos is required")
    @Schema(
            description = "List of order items",
            example = "[{\"productId\":1,\"quantity\":2}]",
            name = "orderItems",
            type = "List")
    private Set<OrderItemDto> orderItems = new HashSet<>();
}
