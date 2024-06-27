package com.thanhquang.sourcebase.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.thanhquang.sourcebase.dto.response.user.UserDto;
import com.thanhquang.sourcebase.entities.UserEntity;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(UserEntity userEntity);
}
