package com.onlineshopping.orderservice.controller;

import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder (@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping
    public List<OrderRequest> getAllOrders() {
        return orderService.getAllOrders();
    }
}
