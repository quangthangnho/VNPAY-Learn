package com.thanhquang.sourcebase.services;

import com.thanhquang.sourcebase.dto.request.auth.LoginDto;
import com.thanhquang.sourcebase.dto.request.auth.RegisterDto;
import com.thanhquang.sourcebase.dto.response.auth.JwtResDto;
import com.thanhquang.sourcebase.dto.response.user.UserDto;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface AuthService {

    UserDto register(RegisterDto registerDto) throws BadRequestException;

    JwtResDto login(LoginDto loginDto) throws BadRequestException;

    JwtResDto refreshToken(String refreshToken) throws BadRequestException;
}
