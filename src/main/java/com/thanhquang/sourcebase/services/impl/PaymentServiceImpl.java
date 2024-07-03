package com.thanhquang.sourcebase.services.impl;

import com.thanhquang.sourcebase.constant.PaymentConstant;
import com.thanhquang.sourcebase.dto.request.payment.PaymentRequestIpnUrlDTO;
import com.thanhquang.sourcebase.dto.response.payment.VnpayResPINUrlDto;
import com.thanhquang.sourcebase.entities.OrderEntity;
import com.thanhquang.sourcebase.entities.PaymentEntity;
import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.enums.order.OrderStatus;
import com.thanhquang.sourcebase.enums.payment.PaymentMethod;
import com.thanhquang.sourcebase.enums.payment.PaymentStatus;
import com.thanhquang.sourcebase.enums.vnpay.VnpayCommand;
import com.thanhquang.sourcebase.enums.vnpay.VnpayLocale;
import com.thanhquang.sourcebase.enums.vnpay.VnpayParams;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.AuthenticationErrors;
import com.thanhquang.sourcebase.repositories.PaymentRepository;
import com.thanhquang.sourcebase.services.OrderService;
import com.thanhquang.sourcebase.services.PaymentService;
import com.thanhquang.sourcebase.utils.CommonUtils;
import com.thanhquang.sourcebase.utils.VnpayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.thanhquang.sourcebase.enums.vnpay.VnpayParams.*;

/**
 * @author admin
 */
@Slf4j
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
    public String makePayment(String orderCode, HttpServletRequest request)
            throws BadRequestException, UnsupportedEncodingException {
        OrderEntity order = orderService.checkOrder(orderCode);

        OffsetDateTime dateTimeInZone7 = OffsetDateTime.now(ZoneOffset.of("+07:00"));
        String createdTime = formatDate(dateTimeInZone7);
        String expiredTime = formatDate(dateTimeInZone7.plusMinutes(15));

        PaymentEntity payment = PaymentEntity.builder()
                .order(order)
                .amount(order.getOrderTotal())
                .paymentMethod(PaymentMethod.VNPAY)
                .paymentStatus(PaymentStatus.PENDING)
                .user(getUserLogin())
                .paymentCode("payment-code" + UUID.randomUUID().toString().replace("-", ""))
                .build();
        paymentRepository.save(payment);

        Map<String, String> params =
                createPaymentParams(payment.getPaymentCode(), request, order, createdTime, expiredTime);
        String queryUrl = buildQueryUrl(params);
        String vnpSecureHash = VnpayUtils.hmacSHA512(vnpHashSecret, queryUrl);
        return vnpUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnpSecureHash;
    }

    @Transactional
    @Override
    public VnpayResPINUrlDto handleIpnUrl(PaymentRequestIpnUrlDTO paymentRequestIpnUrlDTO) {
        log.info("Handle IPN URL: {}", paymentRequestIpnUrlDTO.toString());
        Map<String, String> fields = mapPaymentRequestIpnUrlDTO(paymentRequestIpnUrlDTO);
        if (!VnpayUtils.hashAllFields(fields, vnpHashSecret).equals(paymentRequestIpnUrlDTO.getVnpSecureHash())) {
            return new VnpayResPINUrlDto("97", "Invalid Checksum");
        }
        return processPayment(paymentRequestIpnUrlDTO);
    }

    private Map<String, String> mapPaymentRequestIpnUrlDTO(PaymentRequestIpnUrlDTO dto) {
        Map<String, String> fields = new HashMap<>();
        fields.put(encodeUsAscii(VnpayParams.VNP_AMOUNT), encodeUsAscii(dto.getVnpAmount()));
        fields.put(encodeUsAscii(VnpayParams.VNP_BANKCODE), encodeUsAscii(dto.getVnpBankCode()));
        fields.put(encodeUsAscii(VnpayParams.VNP_BANKTRANNO), encodeUsAscii(dto.getVnpBankTranNo()));
        fields.put(encodeUsAscii(VnpayParams.VNP_CARDTYPE), encodeUsAscii(dto.getVnpCardType()));
        fields.put(encodeUsAscii(VnpayParams.VNP_ORDERINFO), encodeUsAscii(dto.getVnpOrderInfo()));
        fields.put(encodeUsAscii(VnpayParams.VNP_PAYDATE), encodeUsAscii(dto.getVnpPayDate()));
        fields.put(encodeUsAscii(VnpayParams.VNP_RESPONSECODE), encodeUsAscii(dto.getVnpResponseCode()));
        fields.put(encodeUsAscii(VnpayParams.VNP_TMNCODE), encodeUsAscii(dto.getVnpTmnCode()));
        fields.put(encodeUsAscii(VnpayParams.VNP_TRANSACTIONNO), encodeUsAscii(dto.getVnpTransactionNo()));
        fields.put(encodeUsAscii(VnpayParams.VNP_TXNREF), encodeUsAscii(dto.getVnpTxnRef()));
        fields.put(encodeUsAscii(VnpayParams.VNP_TRANSACTION_STATUS), encodeUsAscii(dto.getVnpTransactionStatus()));
        return fields;
    }

    private VnpayResPINUrlDto processPayment(PaymentRequestIpnUrlDTO dto) {
        Optional<PaymentEntity> paymentOpt = paymentRepository.findByPaymentCode(dto.getVnpTxnRef());
        if (paymentOpt.isEmpty()) {
            return new VnpayResPINUrlDto("01", "Payment Order not Found");
        }
        PaymentEntity payment = paymentOpt.get();
        OrderEntity order = payment.getOrder();
        if (order.getOrderTotal() != Long.parseLong(dto.getVnpAmount())) {
            return new VnpayResPINUrlDto("04", "Invalid Amount");
        }
        if (payment.getPaymentStatus() != PaymentStatus.PENDING || order.getOrderStatus() != OrderStatus.PENDING) {
            return new VnpayResPINUrlDto("02", "Payment Order is already confirmed");
        }
        return updatePaymentAndOrderStatus(dto, payment, order);
    }

    private VnpayResPINUrlDto updatePaymentAndOrderStatus(
            PaymentRequestIpnUrlDTO dto, PaymentEntity payment, OrderEntity order) {
        boolean isSuccess = "00".equals(dto.getVnpResponseCode()) && "00".equals(dto.getVnpTransactionStatus());
        payment.setPaymentStatus(isSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        order.setOrderStatus(isSuccess ? OrderStatus.COMPLETED : OrderStatus.FAILED);
        paymentRepository.save(payment);
        orderService.saves(Collections.singletonList(order));
        return new VnpayResPINUrlDto(isSuccess ? "00" : "01", isSuccess ? "Success" : "failed");
    }

    private Map<String, String> createPaymentParams(
            String paymentCode, HttpServletRequest request, OrderEntity order, String createdTime, String expiredTime) {
        Map<String, String> params = new HashMap<>();
        params.put(VNP_VERSION, PaymentConstant.VNP_VERSION);
        params.put(VNP_COMMAND, VnpayCommand.PAY.getValue());
        params.put(VNP_TMNCODE, vnpTmnCode);
        params.put(VNP_AMOUNT, String.valueOf(order.getOrderTotal()));
        params.put(VNP_CREATEDATE, createdTime);
        params.put(VNP_CURRCODE, PaymentConstant.VNP_CURR_CODE);
        params.put(VNP_IPADDR, CommonUtils.getIpAddress(request));
        params.put(VNP_LOCALE, VnpayLocale.VN.getValue());
        params.put(
                VNP_ORDERINFO,
                "THANH TOAN MUA HANG VOI MA DON HANG " + paymentCode + " SO TIEN THANH TOAN: " + order.getOrderTotal()
                        + " VND");
        params.put(VNP_ORDERTYPE, "110000");
        params.put(VNP_RETURNURL, "https://google.com.vn");
        params.put(VNP_BANKCODE, "");
        params.put(VNP_EXPIREDATE, expiredTime);
        params.put(VNP_TXNREF, paymentCode);
        return params;
    }

    private String buildQueryUrl(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(encodeUsAscii(fieldValue));
                // Build query
                query.append(encodeUsAscii(fieldName));
                query.append('=');
                query.append(encodeUsAscii(fieldValue));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        return query.toString();
    }

    private String formatDate(OffsetDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private UserEntity getUserLogin() throws BadRequestException {
        return CommonUtils.getPrincipal()
                .orElseThrow(() -> new BadRequestException(AuthenticationErrors.USER_NOT_FOUND))
                .getUser();
    }

    private String encodeUsAscii(String data) {
        return URLEncoder.encode(data, StandardCharsets.US_ASCII);
    }
}
