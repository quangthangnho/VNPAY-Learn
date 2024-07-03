package com.thanhquang.sourcebase.controllers.user;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanhquang.sourcebase.dto.response.common.ApiResponse;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/payments")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Payment", description = "Payment APIs")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Make payment - method PAY")
    @GetMapping()
    public ApiResponse<String> makePayment(
            @RequestParam(value = "orderCode", required = true) String orderCode, HttpServletRequest request)
            throws BadRequestException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return ApiResponse.success(paymentService.makePayment(orderCode, request));
    }
}
