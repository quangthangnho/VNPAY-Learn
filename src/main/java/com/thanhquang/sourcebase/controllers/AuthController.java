package com.thanhquang.sourcebase.controllers;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanhquang.sourcebase.dto.request.auth.LoginDto;
import com.thanhquang.sourcebase.dto.request.auth.RefreshTokenDto;
import com.thanhquang.sourcebase.dto.request.auth.RegisterDto;
import com.thanhquang.sourcebase.dto.response.auth.JwtResDto;
import com.thanhquang.sourcebase.dto.response.common.ApiResponse;
import com.thanhquang.sourcebase.dto.response.user.UserDto;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth management", description = "Auth management API")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ApiResponse<JwtResDto> login(@Valid @RequestBody LoginDto loginDto) throws BadRequestException {
        return ApiResponse.success(authService.login(loginDto));
    }

    @Operation(summary = "Register")
    @PostMapping("/register")
    public ApiResponse<UserDto> register(@Valid @RequestBody RegisterDto registerDto) throws BadRequestException {
        return ApiResponse.success(authService.register(registerDto));
    }

    @Operation(summary = "Refresh token")
    @PostMapping("/refresh-token")
    public ApiResponse<JwtResDto> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto)
            throws BadRequestException {
        return ApiResponse.success(authService.refreshToken(refreshTokenDto.getRefreshToken()));
    }
}
