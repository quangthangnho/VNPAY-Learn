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
    public ApiResponse<VnpayResPINUrlDto> handleIpnUrl(@RequestParam(value = "vnp_Amount", required = false) String vnpAmount,
                                                       @RequestParam(value = "vnp_BankCode", required = false) String vnpBankCode,
                                                       @RequestParam(value = "vnp_BankTranNo", required = false) String vnpBankTranNo,
                                                       @RequestParam(value = "vnp_CardType", required = false) String vnpCardType,
                                                       @RequestParam(value = "vnp_OrderInfo", required = false) String vnpOrderInfo,
                                                       @RequestParam(value = "vnp_PayDate", required = false) String vnpPayDate,
                                                       @RequestParam(value = "vnp_ResponseCode", required = false) String vnpResponseCode,
                                                       @RequestParam(value = "vnp_TmnCode", required = false) String vnpTmnCode,
                                                       @RequestParam(value = "vnp_TransactionNo", required = false) String vnpTransactionNo,
                                                       @RequestParam(value = "vnp_TransactionStatus", required = false) String vnpTransactionStatus,
                                                       @RequestParam(value = "vnp_TxnRef", required = false) String vnpTxnRef,
                                                       @RequestParam(value = "vnp_SecureHashType", required = false) String vnpSecureHashType,
                                                       @RequestParam(value = "vnp_SecureHash", required = false) String vnpSecureHash)

    {
        return ApiResponse.success(paymentService.handleIpnUrl(new PaymentRequestIpnUrlDTO(vnpAmount, vnpBankCode, vnpBankTranNo, vnpCardType, vnpOrderInfo, vnpPayDate, vnpResponseCode, vnpTmnCode, vnpTransactionNo, vnpTransactionStatus, vnpTxnRef, vnpSecureHashType, vnpSecureHash)));
    }
}
