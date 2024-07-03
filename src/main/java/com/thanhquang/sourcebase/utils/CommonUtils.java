package com.thanhquang.sourcebase.utils;

import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.thanhquang.sourcebase.services.impl.user_detail.UserDetailsImpl;

public class CommonUtils {

    private CommonUtils() {}

    public static Optional<UserDetailsImpl> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userdetailsimpl) {
            return Optional.of(userdetailsimpl);
        }
        return Optional.empty();
    }

    public static String generateRandomOrderCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        return "order-" + uuid.substring(0, 3) + "-" + uuid.substring(3);
    }

    public static String getIpAddress(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
    }
}
