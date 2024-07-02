package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;
import com.thanhquang.sourcebase.enums.order.OrderStatus;

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
    private OffsetDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_order_status", nullable = false, columnDefinition = "order_status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private OrderStatus orderStatus;

    @Column(name = "col_order_total", nullable = false)
    private Long orderTotal;

    @Column(name = "col_order_code", unique = true, nullable = false)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "col_user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderItemEntity> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PaymentEntity> payments;

    public void addOrderItem(OrderItemEntity orderItem) {
        if (orderItems == null) {
            orderItems = new HashSet<>();
        }
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItemEntity orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public void addPayment(PaymentEntity payment) {
        payments.add(payment);
        payment.setOrder(this);
    }

    public void removePayment(PaymentEntity payment) {
        payments.remove(payment);
        payment.setOrder(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public static OrderEntity createNewOrder(UserEntity user, String orderCode) {
        return OrderEntity.builder()
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .orderCode(orderCode)
                .orderDate(OffsetDateTime.now())
                .orderTotal(0L)
                .build();
    }
}
