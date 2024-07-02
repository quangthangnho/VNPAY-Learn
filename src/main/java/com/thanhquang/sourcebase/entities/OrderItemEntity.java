package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;

import lombok.*;

@Entity
@Table(name = "tbl_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity extends BaseEntityAudit implements Serializable {

    @Column(name = "col_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "col_price", nullable = false)
    private Long price;

    @Column(name = "col_total_price", nullable = false)
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "col_order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "col_product_id")
    private ProductEntity product;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItemEntity that = (OrderItemEntity) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
