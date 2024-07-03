package com.thanhquang.sourcebase.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

import com.thanhquang.sourcebase.dto.request.payment.PaymentRequestIpnUrlDTO;
import com.thanhquang.sourcebase.dto.response.payment.VnpayResPINUrlDto;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface PaymentService {

    String makePayment(String orderCode, HttpServletRequest request)
            throws BadRequestException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

    VnpayResPINUrlDto handleIpnUrl(PaymentRequestIpnUrlDTO paymentRequestIpnUrlDTO);
}
