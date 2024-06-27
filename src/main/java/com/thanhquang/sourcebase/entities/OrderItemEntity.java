package com.thanhquang.sourcebase.entities;

import java.io.Serializable;

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

    @Column(name = "col_order_id", nullable = false)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "col_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderEntity order;

    @Column(name = "col_product_id", nullable = false)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "col_product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;
}
