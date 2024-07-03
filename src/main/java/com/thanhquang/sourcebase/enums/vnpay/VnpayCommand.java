package com.thanhquang.sourcebase.enums.vnpay;

import lombok.Getter;

@Getter
public enum VnpayCommand {
    PAY("pay"),
    TOKEN_CREATE("token_create");

    final String value;

    VnpayCommand(String value) {
        this.value = value;
    }
}
