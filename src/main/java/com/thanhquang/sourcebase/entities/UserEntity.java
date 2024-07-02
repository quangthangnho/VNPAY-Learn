package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.thanhquang.sourcebase.entities.base.BaseEntityAudit;
import com.thanhquang.sourcebase.enums.user.UserStatus;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntityAudit implements Serializable {

    @Column(name = "col_status", nullable = false, columnDefinition = "user_status")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserStatus status;

    @Column(name = "col_email", nullable = false, unique = true)
    private String email;

    @Column(name = "col_password", nullable = false)
    private String password;

    @Column(name = "col_full_name")
    private String fullName;

    @Column(name = "col_phone_number", unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PaymentEntity> payments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderEntity> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private RefreshTokenEntity refreshToken;

    public void addUserRole(UserRoleEntity userRole) {
        if (userRoles == null) {
            userRoles = new HashSet<>();
        }
        userRoles.add(userRole);
        userRole.setUser(this);
    }

    public void removeUserRole(UserRoleEntity userRole) {
        userRoles.remove(userRole);
        userRole.setUser(null);
    }

    public void addPayment(PaymentEntity payment) {
        if (payments == null) {
            payments = new HashSet<>();
        }
        payments.add(payment);
        payment.setUser(this);
    }

    public void removePayment(PaymentEntity payment) {
        payments.remove(payment);
        payment.setUser(null);
    }

    public void addOrder(OrderEntity order) {
        if (orders == null) {
            orders = new HashSet<>();
        }
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(OrderEntity order) {
        orders.remove(order);
        order.setUser(null);
    }
}
