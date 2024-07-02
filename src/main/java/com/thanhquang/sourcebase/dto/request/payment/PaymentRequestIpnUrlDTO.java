package com.thanhquang.sourcebase.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequestIpnUrlDTO {

    @JsonProperty("vnp_Amount")
    private String vnpAmount;

    @JsonProperty("vnp_BankCode")
    private String vnpBankCode;

    @JsonProperty("vnp_BankTranNo")
    private String vnpBankTranNo;

    @JsonProperty("vnp_CardType")
    private String vnpCardType;

    @JsonProperty("vnp_OrderInfo")
    private String vnpOrderInfo;

    @JsonProperty("vnp_PayDate")
    private String vnpPayDate;

    @JsonProperty("vnp_ResponseCode")
    private String vnpResponseCode;

    @JsonProperty("vnp_TmnCode")
    private String vnpTmnCode;

    @JsonProperty("vnp_TransactionNo")
    private String vnpTransactionNo;

    @JsonProperty("vnp_TxnRef")
    private String vnpTxnRef;

    @JsonProperty("vnp_SecureHashType")
    private String vnpSecureHashType;

    @JsonProperty("vnp_SecureHash")
    private String vnpSecureHash;
}
