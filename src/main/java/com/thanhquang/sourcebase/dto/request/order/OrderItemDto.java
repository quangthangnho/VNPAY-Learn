package com.thanhquang.sourcebase.dto.request.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    @NotEmpty(message = "productId is required")
    @Schema(description = "productId", example = "1", name = "productId", type = "Long")
    private Long productId;

    @NotEmpty(message = "quantity is required")
    @Min(value = 1, message = "quantity must be greater than 0")
    @Schema(description = "quantity", example = "1", name = "quantity", type = "Integer")
    private Integer quantity;

    // create equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderItemDto that)) {
            return false;
        }
        return getProductId().equals(that.getProductId());
    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }
}
