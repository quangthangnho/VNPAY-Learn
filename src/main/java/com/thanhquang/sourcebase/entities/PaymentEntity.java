package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;
import com.thanhquang.sourcebase.enums.payment.PaymentMethod;
import com.thanhquang.sourcebase.enums.payment.PaymentStatus;

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
    @Column(name = "col_payment_method", nullable = false, columnDefinition = "enum_payment_method")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "col_payment_status", nullable = false, columnDefinition = "enum_payment_status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private PaymentStatus paymentStatus;

    @Column(name = "col_transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "col_payment_code", unique = true, nullable = false)
    private String paymentCode;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private OrderEntity order;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
