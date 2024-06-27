package com.thanhquang.sourcebase.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDto {

    @NotEmpty(message = "Refresh token is required")
    @Schema(
            description = "RefreshToken",
            example =
                    "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMWI2Y2Q1ZS0xNmE1LTQ4YmQtODNjMC0xYmYzZmUxMzhlNGMiLCJpc3MiOiJpc3N1ZXJfcXVhbmciLCJzdWIiOiJzeWxhczFAZ21haWwuY29tIiwiaWF0IjoxNzA5NTM1NjQzLCJleHAiOjE3MDk3MDg0NDN9.A1JJuCMuJ9i55EokiOJdhXbDxBcL-oT_8ynNXayZsP4",
            name = "refreshToken",
            type = "string")
    private String refreshToken;
}
