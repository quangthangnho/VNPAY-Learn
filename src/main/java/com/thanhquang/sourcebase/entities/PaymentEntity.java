package com.thanhquang.sourcebase.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.constant.payment.PaymentMethod;
import com.thanhquang.sourcebase.constant.payment.PaymentStatus;
import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;

import lombok.*;

@Entity
@Table(name = "tbl_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity extends BaseEntityAudit implements Serializable {

    @Column(name = "col_amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "col_transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "col_payment_code", unique = true, nullable = false)
    private String paymentCode;

    @Column(name = "col_user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "col_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "col_order_id", nullable = false)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "col_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderEntity order;
}
