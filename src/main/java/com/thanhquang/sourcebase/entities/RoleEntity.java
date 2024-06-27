package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.entities.base.BaseEntity;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_roles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends BaseEntity implements Serializable {

    @Column(name = "col_role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "col_description")
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRoleEntity> userRoles;
}
