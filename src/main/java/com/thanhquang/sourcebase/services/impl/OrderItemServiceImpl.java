package com.thanhquang.sourcebase.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thanhquang.sourcebase.entities.OrderItemEntity;
import com.thanhquang.sourcebase.repositories.OrderItemRepository;
import com.thanhquang.sourcebase.services.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void saveAll(List<OrderItemEntity> orderItem) {
        orderItemRepository.saveAll(orderItem);
    }

    @Override
    public OrderItemEntity save(OrderItemEntity orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
