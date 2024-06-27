package com.thanhquang.sourcebase.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.*;

import com.thanhquang.sourcebase.entities.base.BaseEntity;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_refresh_tokens")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity extends BaseEntity implements Serializable {

    @Column(name = "col_token", nullable = false, unique = true)
    private String token;

    @Column(name = "col_expired_at", nullable = false)
    private OffsetDateTime expiredAt;

    @OneToOne
    @JoinColumn(name = "col_user_id", referencedColumnName = "id")
    private UserEntity user;
}
