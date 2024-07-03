package com.thanhquang.sourcebase.services.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thanhquang.sourcebase.dto.request.order.CreateOrderDto;
import com.thanhquang.sourcebase.dto.request.order.OrderItemDto;
import com.thanhquang.sourcebase.dto.response.order.OrderResDto;
import com.thanhquang.sourcebase.entities.OrderEntity;
import com.thanhquang.sourcebase.entities.OrderItemEntity;
import com.thanhquang.sourcebase.entities.ProductEntity;
import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.enums.order.OrderStatus;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.AuthenticationErrors;
import com.thanhquang.sourcebase.exceptions.error_code.impl.OrderErrors;
import com.thanhquang.sourcebase.mapper.OrderMapper;
import com.thanhquang.sourcebase.repositories.OrderRepository;
import com.thanhquang.sourcebase.services.OrderItemService;
import com.thanhquang.sourcebase.services.OrderService;
import com.thanhquang.sourcebase.services.ProductService;
import com.thanhquang.sourcebase.utils.CommonUtils;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderServiceImpl(
            OrderRepository orderRepository, ProductService productService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Transactional
    @Override
    public OrderResDto createOrder(CreateOrderDto createOrderDto) throws BadRequestException {
        this.validateOrderItems(createOrderDto);
        UserEntity userLogin = this.getUserLogin();
        OrderEntity orderEntity = this.createNewOrder(userLogin);
        orderEntity = orderRepository.save(orderEntity);
        this.addOrderItems(createOrderDto, orderEntity);
        orderRepository.save(orderEntity);
        return OrderMapper.INSTANCE.toOrderResDto(this.findOrderById(orderEntity.getId()));
    }

    @Override
    public OrderEntity checkOrder(String orderCode) throws BadRequestException {
        return orderRepository
                .findByOrderCode(orderCode)
                .orElseThrow(() -> new BadRequestException(OrderErrors.ORDER_NOT_FOUND));
    }

    @Override
    public OrderEntity checkOrderByOrderCodeAndStatus(String orderCode, OrderStatus orderStatus)
            throws BadRequestException {
        return orderRepository
                .findByOrderCodeAndOrderStatus(orderCode, orderStatus)
                .orElse(null);
    }

    @Override
    public List<OrderEntity> saves(List<OrderEntity> orderEntity) {
        return orderRepository.saveAll(orderEntity);
    }

    private OrderEntity findOrderById(Long orderId) throws BadRequestException {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new BadRequestException(OrderErrors.ORDER_NOT_FOUND));
    }

    private void validateOrderItems(CreateOrderDto createOrderDto) throws BadRequestException {
        if (Objects.isNull(createOrderDto.getOrderItems())) {
            throw new BadRequestException(OrderErrors.ORDER_ITEMS_EMPTY);
        }
    }

    private UserEntity getUserLogin() throws BadRequestException {
        return CommonUtils.getPrincipal()
                .orElseThrow(() -> new BadRequestException(AuthenticationErrors.USER_NOT_FOUND))
                .getUser();
    }

    private OrderEntity createNewOrder(UserEntity userLogin) {
        String randomOrderCode = CommonUtils.generateRandomOrderCode();
        return OrderEntity.createNewOrder(userLogin, randomOrderCode);
    }

    private void addOrderItems(CreateOrderDto createOrderDto, OrderEntity orderEntity) throws BadRequestException {
        for (OrderItemDto orderItem : createOrderDto.getOrderItems()) {
            ProductEntity product = productService.findProductById(orderItem.getProductId());
            OrderItemEntity orderItemEntity = this.createOrderItemEntity(orderItem, product);
            orderEntity.addOrderItem(orderItemEntity);
            product.addOrderItem(orderItemEntity);
            orderItemService.save(orderItemEntity);
            orderEntity.setOrderTotal(orderEntity.getOrderTotal() + orderItemEntity.getTotalPrice());
        }
    }

    private OrderItemEntity createOrderItemEntity(OrderItemDto orderItem, ProductEntity product) {
        return OrderItemEntity.builder()
                .quantity(orderItem.getQuantity())
                .price(product.getPrice())
                .totalPrice(product.getPrice() * orderItem.getQuantity())
                .build();
    }
}
