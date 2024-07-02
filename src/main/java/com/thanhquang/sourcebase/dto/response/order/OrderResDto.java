package com.thanhquang.sourcebase.dto.response.order;

import java.time.OffsetDateTime;
import java.util.List;

import com.thanhquang.sourcebase.dto.response.BaseDto;
import com.thanhquang.sourcebase.dto.response.user.UserDto;
import com.thanhquang.sourcebase.enums.order.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResDto extends BaseDto {

    private OffsetDateTime orderDate;
    private OrderStatus orderStatus;
    private Long orderTotal;
    private String orderCode;
    private UserDto user;
    private List<OrderItemResDto> orderItems;
}
