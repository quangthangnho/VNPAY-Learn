package com.thanhquang.sourcebase.entities.base;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import com.thanhquang.sourcebase.constant.CommonConstant;
import com.thanhquang.sourcebase.services.impl.user_detail.UserDetailsImpl;
import com.thanhquang.sourcebase.utils.CommonUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntityAudit extends BaseEntity implements Serializable {

    @Column(name = "col_created_by")
    private String createdBy;

    @Column(name = "col_updated_by")
    private String updatedBy;

    @Column(name = "col_deleted_by")
    private String deletedBy;

    @Column(name = "col_created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(name = "col_updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;

    @Column(name = "col_deleted_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime deletedAt;

    @PrePersist
    public void onPrePersist() {
        String email = getEmailFromPrincipal();
        setCreatedAtAndCreatedBy(email);
        setUpdatedAtAndUpdatedBy(email);
    }

    @PreUpdate
    public void onPreUpdate() {
        String email = getEmailFromPrincipal();
        setUpdatedAtAndUpdatedBy(email);
    }

    private String getEmailFromPrincipal() {
        return CommonUtils.getPrincipal().map(UserDetailsImpl::getUsername).orElse(CommonConstant.DEFAULT_EMAIL);
    }

    private void setCreatedAtAndCreatedBy(String email) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        this.setCreatedAt(now);
        this.setCreatedBy(email);
    }

    private void setUpdatedAtAndUpdatedBy(String email) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        this.setUpdatedAt(now);
        this.setUpdatedBy(email);
    }
}
