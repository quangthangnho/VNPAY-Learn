package com.thanhquang.sourcebase.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.thanhquang.sourcebase.dto.request.auth.RegisterDto;
import com.thanhquang.sourcebase.entities.UserEntity;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    UserEntity toUserEntityRegister(RegisterDto registerDto);
}
