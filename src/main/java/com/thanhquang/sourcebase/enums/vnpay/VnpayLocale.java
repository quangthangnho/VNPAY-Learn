package com.thanhquang.sourcebase.enums.vnpay;

import lombok.Getter;

@Getter
public enum VnpayLocale {
    VN("vn"),
    EN("en");

    final String value;

    VnpayLocale(String value) {
        this.value = value;
    }
}
