package com.thanhquang.sourcebase.services.impl;

import org.springframework.stereotype.Service;

import com.thanhquang.sourcebase.dto.JwtDto;
import com.thanhquang.sourcebase.entities.RefreshTokenEntity;
import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.AuthenticationErrors;
import com.thanhquang.sourcebase.repositories.RefreshTokenRepository;
import com.thanhquang.sourcebase.services.RefreshTokenService;
import com.thanhquang.sourcebase.services.UserService;
import com.thanhquang.sourcebase.utils.JwtUtils;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    @Override
    public String createRefreshToken(String email) throws BadRequestException {
        UserEntity user = userService.findByEmailAndDeletedAtIsNull(email);
        refreshTokenRepository.deleteTokenByUser(user);
        JwtDto jwtDto = JwtUtils.generateToken(user.getEmail(), false);
        RefreshTokenEntity refreshToken = refreshTokenRepository.save(RefreshTokenEntity.builder()
                .token(jwtDto.getToken())
                .expiredAt(jwtDto.getExpiredAt())
                .user(user)
                .build());
        return refreshToken.getToken();
    }

    @Override
    public void verifyRefreshTokenExpiration(String refreshToken) throws BadRequestException {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new BadRequestException(AuthenticationErrors.REFRESH_TOKEN_NOT_FOUND));
        if (!JwtUtils.validateToken(refreshTokenEntity.getToken())) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new BadRequestException(AuthenticationErrors.REFRESH_TOKEN_EXPIRED);
        }
    }
}
