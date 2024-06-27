package com.thanhquang.sourcebase.dto.response.auth;

import com.thanhquang.sourcebase.constant.CommonConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResDto {

    private String accessToken;
    private String refreshToken;
    private String type = CommonConstant.DEFAULT_BEARER;

    public JwtResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
