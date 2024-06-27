package com.thanhquang.sourcebase.utils;

import static com.thanhquang.sourcebase.constant.CommonConstant.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.thanhquang.sourcebase.constant.CommonConstant;
import com.thanhquang.sourcebase.dto.JwtDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

    private JwtUtils() {}

    private static final JwtParser JWT_PARSER =
            Jwts.parser().verifyWith(CommonConstant.SECRET_KEY).build();

    public static JwtDto generateToken(String email, boolean isAccessToken) {
        OffsetDateTime nowOffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime expirationInstant;
        if (isAccessToken) {
            expirationInstant = nowOffsetDateTime.plusMinutes(DEFAULT_ACCESS_TOKEN_EXPIRATION_TIME);
        } else {
            expirationInstant = nowOffsetDateTime.plusDays(DEFAULT_REFRESH_TOKEN_EXPIRATION_TIME);
        }
        String token = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(DEFAULT_TOKEN_ISSUER)
                .subject(email)
                .issuedAt(Date.from(nowOffsetDateTime.toInstant()))
                .expiration(Date.from(expirationInstant.toInstant()))
                .signWith(CommonConstant.SECRET_KEY)
                .compact();
        return JwtDto.builder().token(token).expiredAt(expirationInstant).build();
    }

    private static Optional<Claims> parseJwtToken(String jwtToken) {
        try {
            // Reusing the pre-built and configured JwtParser
            return Optional.of(JWT_PARSER.parseSignedClaims(jwtToken).getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT Exception occurred", e); // Consider logging the exception for better debugging
        }
        return Optional.empty();
    }

    public static boolean validateToken(String jwtToken) {
        Optional<Claims> claimJwts = parseJwtToken(jwtToken);
        return claimJwts
                .map(claims -> claims.getExpiration()
                        .after(Date.from(OffsetDateTime.now().toInstant())))
                .orElse(false);
    }

    public static Optional<String> getEmailFromToken(String jwtToken) {
        return parseJwtToken(jwtToken).map(Claims::getSubject);
    }

    public static Optional<String> getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(CommonConstant.DEFAULT_BEARER)) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }
}
