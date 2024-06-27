package com.thanhquang.sourcebase.dto;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtDto {

    private String token;
    private OffsetDateTime expiredAt;
}
