package com.thanhquang.sourcebase.config;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thanhquang.sourcebase.utils.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    /**
     * This method performs authentication and sets the security context based on the JWT token.
     *
     * @param request     the HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        logRequestHeaders(request);
        JwtUtils.getTokenFromRequest(request)
                .filter(JwtUtils::validateToken)
                .flatMap(JwtUtils::getEmailFromToken)
                .map(userDetailsService::loadUserByUsername)
                .map(userDetails ->
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()))
                .ifPresent(authenticationToken -> {
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
        filterChain.doFilter(request, response);
    }

    private void logRequestHeaders(HttpServletRequest request) {
        log.info("===== Logging Request Headers START =====");
        log.info("Request URL: {}", request.getRequestURI());

        Enumeration<String> headerNames = request.getHeaderNames();

        log.info("===== Request Headers =====");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("{}: {}", headerName, headerValue);
        }
        log.info("=============================");
    }
}
