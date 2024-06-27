package com.thanhquang.sourcebase.utils;

import java.util.Optional;

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
}
