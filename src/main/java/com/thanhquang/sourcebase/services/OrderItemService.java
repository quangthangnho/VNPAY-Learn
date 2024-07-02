package com.thanhquang.sourcebase.services;

import java.util.List;

import com.thanhquang.sourcebase.entities.OrderItemEntity;

public interface OrderItemService {

    void saveAll(List<OrderItemEntity> orderItem);

    OrderItemEntity save(OrderItemEntity orderItem);
}
