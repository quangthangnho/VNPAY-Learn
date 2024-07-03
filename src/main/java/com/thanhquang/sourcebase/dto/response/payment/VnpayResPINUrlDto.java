package com.thanhquang.sourcebase.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VnpayResPINUrlDto {
    private String RspCode;
    private String Message;
}
