package com.thanhquang.sourcebase.services;

import java.util.List;

import com.thanhquang.sourcebase.entities.ProductEntity;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface ProductService {

    List<ProductEntity> findProductByIds(List<Long> productIds) throws BadRequestException;

    ProductEntity findProductById(Long productId) throws BadRequestException;

    ProductEntity save(ProductEntity productEntity);
}
