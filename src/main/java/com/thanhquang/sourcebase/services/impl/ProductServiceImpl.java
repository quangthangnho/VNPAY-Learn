package com.thanhquang.sourcebase.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thanhquang.sourcebase.entities.ProductEntity;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.ProductErrors;
import com.thanhquang.sourcebase.repositories.ProductRepository;
import com.thanhquang.sourcebase.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> findProductByIds(List<Long> productIds) throws BadRequestException {
        return getProducts(productIds);
    }

    @Override
    public ProductEntity findProductById(Long productId) throws BadRequestException {
        return getProduct(productId);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    private List<ProductEntity> getProducts(List<Long> productIds) throws BadRequestException {
        List<ProductEntity> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new BadRequestException(ProductErrors.PRODUCT_NOT_FOUND);
        }
        return products;
    }

    private ProductEntity getProduct(Long productId) throws BadRequestException {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new BadRequestException(ProductErrors.PRODUCT_NOT_FOUND));
    }
}
