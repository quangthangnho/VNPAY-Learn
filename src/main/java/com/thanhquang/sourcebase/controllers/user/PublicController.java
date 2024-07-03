package com.thanhquang.sourcebase.controllers.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import com.thanhquang.sourcebase.dto.request.payment.PaymentRequestIpnUrlDTO;
import com.thanhquang.sourcebase.dto.response.common.ApiResponse;
import com.thanhquang.sourcebase.dto.response.payment.VnpayResPINUrlDto;
import com.thanhquang.sourcebase.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/public")
@Tag(name = "Public", description = "Public APIs")
public class PublicController {

    private final PaymentService paymentService;

    public PublicController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "IPN URL")
    @GetMapping("/IPN")
    public ApiResponse<VnpayResPINUrlDto> handleIpnUrl(@RequestParam(value = "vnp_Amount") String vnpAmount,
                                                       @RequestParam(value = "vnp_BankCode") String vnpBankCode,
                                                       @RequestParam(value = "vnp_BankTranNo") String vnpBankTranNo,
                                                       @RequestParam(value = "vnp_CardType") String vnpCardType,
                                                       @RequestParam(value = "vnp_OrderInfo") String vnpOrderInfo,
                                                       @RequestParam(value = "vnp_PayDate") String vnpPayDate,
                                                       @RequestParam(value = "vnp_ResponseCode") String vnpResponseCode,
                                                       @RequestParam(value = "vnp_TmnCode") String vnpTmnCode,
                                                       @RequestParam(value = "vnp_TransactionNo") String vnpTransactionNo,
                                                       @RequestParam(value = "vnp_TransactionStatus") String vnpTransactionStatus,
                                                       @RequestParam(value = "vnp_TxnRef") String vnpTxnRef,
                                                       @RequestParam(value = "vnp_SecureHashType", required = false) String vnpSecureHashType,
                                                       @RequestParam(value = "vnp_SecureHash") String vnpSecureHash)

    {
        return ApiResponse.success(paymentService.handleIpnUrl(new PaymentRequestIpnUrlDTO(vnpAmount, vnpBankCode, vnpBankTranNo, vnpCardType, vnpOrderInfo, vnpPayDate, vnpResponseCode, vnpTmnCode, vnpTransactionNo, vnpTransactionStatus, vnpTxnRef, vnpSecureHashType, vnpSecureHash)));
    }
}
