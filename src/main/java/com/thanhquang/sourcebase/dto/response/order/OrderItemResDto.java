package com.thanhquang.sourcebase.dto.response.order;

import com.thanhquang.sourcebase.dto.response.BaseDto;
import com.thanhquang.sourcebase.dto.response.product.ProductResDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResDto extends BaseDto {

    private Integer quantity;
    private Long price;
    private Long totalPrice;
    private ProductResDto product;
}
