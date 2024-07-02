package com.thanhquang.sourcebase.services;

import com.thanhquang.sourcebase.dto.request.payment.PaymentRequestIpnUrlDTO;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaymentService {

    String makePayment(String orderCode, HttpServletRequest request) throws BadRequestException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

    String handleIpnUrl(PaymentRequestIpnUrlDTO paymentRequestIpnUrlDTO);
}
