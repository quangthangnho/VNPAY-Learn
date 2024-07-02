package com.thanhquang.sourcebase.enums.vnpay;

public enum VnpayBankCode {

    VN_PAY_QR("VNPAYQR"),  // Thanh toán quét mã QR
    VN_BANK("VNBANK"),  //Thẻ ATM - Tài khoản ngân hàng nội địa
    INT_CARD("INTCARD")  // Thẻ thanh toán quốc tế
    ;

    final String value;

    VnpayBankCode(String value) {
        this.value = value;
    }

}
