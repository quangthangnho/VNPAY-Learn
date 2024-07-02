package com.thanhquang.sourcebase.controllers.user;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.thanhquang.sourcebase.dto.request.order.CreateOrderDto;
import com.thanhquang.sourcebase.dto.response.common.ApiResponse;
import com.thanhquang.sourcebase.dto.response.order.OrderResDto;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/orders")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Orders management", description = "Orders management API")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create order")
    @PostMapping()
    public ApiResponse<OrderResDto> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto)
            throws BadRequestException {
        return ApiResponse.success(orderService.createOrder(createOrderDto));
    }

    @Operation(summary = "Get order by user")
    @GetMapping()
    public ApiResponse<String> findOrders() throws BadRequestException {
        return ApiResponse.success("hello order ne");
    }
}
