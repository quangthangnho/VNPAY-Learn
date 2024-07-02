package com.thanhquang.sourcebase.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.thanhquang.sourcebase.dto.response.order.OrderResDto;
import com.thanhquang.sourcebase.entities.OrderEntity;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResDto toOrderResDto(OrderEntity order);
}
