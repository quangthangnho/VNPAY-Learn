package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.thanhquang.sourcebase.entities.base.BaseEntity;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_users_roles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "col_user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "col_role_id")
    private RoleEntity role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRoleEntity that = (UserRoleEntity) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
