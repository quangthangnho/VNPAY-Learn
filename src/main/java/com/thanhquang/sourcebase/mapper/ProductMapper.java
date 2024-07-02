package com.thanhquang.sourcebase.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.thanhquang.sourcebase.dto.response.product.ProductResDto;
import com.thanhquang.sourcebase.entities.ProductEntity;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<ProductResDto> toProductsDto(List<ProductEntity> productsEntity);

    ProductResDto toProductDto(ProductEntity productEntity);
}
