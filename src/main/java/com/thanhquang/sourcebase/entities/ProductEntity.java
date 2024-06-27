package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;

import lombok.*;

@Table(name = "tbl_products")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntityAudit implements Serializable {

    @Column(name = "col_name", nullable = false)
    private String name;

    @Column(name = "col_description")
    private String description;

    @Column(name = "col_price", nullable = false)
    private Long price;

    @Column(name = "col_product_code", unique = true, nullable = false)
    private String productCode;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItemEntity> orderItems;
}
