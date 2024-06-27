package com.thanhquang.sourcebase.services;

import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface RefreshTokenService {

    String createRefreshToken(String email) throws BadRequestException;

    void verifyRefreshTokenExpiration(String refreshToken) throws BadRequestException;
}
