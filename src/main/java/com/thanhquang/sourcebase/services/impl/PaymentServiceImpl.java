package com.thanhquang.sourcebase.services.impl;

import com.thanhquang.sourcebase.constant.PaymentConstant;
import com.thanhquang.sourcebase.dto.request.payment.PaymentRequestIpnUrlDTO;
import com.thanhquang.sourcebase.entities.OrderEntity;
import com.thanhquang.sourcebase.enums.vnpay.VnpayCommand;
import com.thanhquang.sourcebase.enums.vnpay.VnpayLocale;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.repositories.PaymentRepository;
import com.thanhquang.sourcebase.services.OrderService;
import com.thanhquang.sourcebase.services.PaymentService;
import com.thanhquang.sourcebase.utils.CommonUtils;
import com.thanhquang.sourcebase.utils.VnpayUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.thanhquang.sourcebase.enums.vnpay.VnpayParams.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${vnp.url}")
    private String vnpUrl;

    @Value("${vnp.tmn-code}")
    private String vnpTmnCode;

    @Value("${vnp.hash-secret}")
    private String vnpHashSecret;

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Override
    public String makePayment(String orderCode, HttpServletRequest request) throws BadRequestException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        OrderEntity order = orderService.checkOrder(orderCode);

        OffsetDateTime dateTimeInZone7 = OffsetDateTime.now(ZoneOffset.of("+07:00"));
        String createdTime = formatDateToYyyyMMddHHmmss(dateTimeInZone7);
        String expiredTime = formatDateToYyyyMMddHHmmss(dateTimeInZone7.plusMinutes(15));

        Map<String, String> params = createPaymentParams(orderCode, request, order, createdTime, expiredTime);
        String queryUrl = buildQueryUrl(params);
        String vnpSecureHash = VnpayUtils.hmacSHA512(vnpHashSecret, queryUrl);

        return vnpUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnpSecureHash;
    }

    @Override
    public String handleIpnUrl(PaymentRequestIpnUrlDTO paymentRequestIpnUrlDTO) {
        return null;
    }

    private Map<String, String> createPaymentParams(String orderCode, HttpServletRequest request, OrderEntity order, String createdTime, String expiredTime) {
        Map<String, String> params = new HashMap<>();
        params.put(VNP_VERSION, PaymentConstant.VNP_VERSION);
        params.put(VNP_COMMAND, VnpayCommand.PAY.getValue());
        params.put(VNP_TMNCODE, vnpTmnCode);
        params.put(VNP_AMOUNT, String.valueOf(order.getOrderTotal()));
        params.put(VNP_CREATEDATE, createdTime);
        params.put(VNP_CURRCODE, PaymentConstant.VNP_CURR_CODE);
        params.put(VNP_IPADDR, CommonUtils.getIpAddress(request));
        params.put(VNP_LOCALE, VnpayLocale.VN.getValue());
        params.put(VNP_ORDERINFO, "THANH TOAN MUA HANG VOI MA DON HANG " + orderCode + " SO TIEN THANH TOAN: " + order.getOrderTotal() + " VND");
        params.put(VNP_ORDERTYPE, "110000");
        params.put(VNP_RETURNURL, "https://google.com.vn");
        params.put(VNP_BANKCODE, "");
        params.put(VNP_EXPIREDATE, expiredTime);
        params.put(VNP_TXNREF, orderCode);
        return params;
    }

    private String buildQueryUrl(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append('&');
            }
        }
        return query.toString();
    }

    private String formatDateToYyyyMMddHHmmss(OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
