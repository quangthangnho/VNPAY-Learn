package com.thanhquang.sourcebase.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhquang.sourcebase.entities.OrderEntity;
import com.thanhquang.sourcebase.enums.order.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderCode(String orderCode);

    Optional<OrderEntity> findByOrderCodeAndOrderStatus(String orderCode, OrderStatus orderStatus);
}
