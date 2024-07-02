package com.thanhquang.sourcebase.enums.vnpay;

public enum VnpayCardType {

    DOMESTIC_CARD("01"), INTERNATIONAL_CARD("02");

    final String value;

    VnpayCardType(String value) {
        this.value = value;
    }
}
