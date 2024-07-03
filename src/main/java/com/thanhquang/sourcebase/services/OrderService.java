package com.thanhquang.sourcebase.services;

import java.util.List;

import com.thanhquang.sourcebase.dto.request.order.CreateOrderDto;
import com.thanhquang.sourcebase.dto.response.order.OrderResDto;
import com.thanhquang.sourcebase.entities.OrderEntity;
import com.thanhquang.sourcebase.enums.order.OrderStatus;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface OrderService {

    OrderResDto createOrder(CreateOrderDto createOrderDto) throws BadRequestException;

    OrderEntity checkOrder(String orderCode) throws BadRequestException;

    OrderEntity checkOrderByOrderCodeAndStatus(String orderCode, OrderStatus orderStatus) throws BadRequestException;

    List<OrderEntity> saves(List<OrderEntity> orderEntity);
}
