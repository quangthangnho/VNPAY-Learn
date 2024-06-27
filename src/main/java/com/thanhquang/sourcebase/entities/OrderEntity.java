package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.constant.order.OrderStatus;
import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;

import lombok.*;

@Entity
@Table(name = "tbl_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity extends BaseEntityAudit implements Serializable {

    @Column(name = "col_order_date", nullable = false)
    private java.time.LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "col_order_total", nullable = false)
    private Long orderTotal;

    @Column(name = "col_order_code", unique = true, nullable = false)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "col_user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItemEntity> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaymentEntity> payments;
}
