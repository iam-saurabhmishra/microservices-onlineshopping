package com.onlineshopping.orderservice.controller;

import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.model.ResponseEnum;
import com.onlineshopping.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final String SUCCESS = "SUCCESS";
    private final String NOT_IN_STOCK = "NOT_IN_STOCK";

    @PostMapping
   // @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory" , fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> placeOrder (@RequestBody OrderRequest orderRequest){
        ResponseEnum responseEnum = orderService.placeOrder(orderRequest);
        if(ResponseEnum.SUCCESS.equals(responseEnum)) {
            return ResponseEntity.ok("Order Placed Successfully");
        }else if(ResponseEnum.NOT_IN_STOCK.equals(responseEnum)) {
            return ResponseEntity.internalServerError().body("Few products are out of stock");
        }else {
            return ResponseEntity.internalServerError().body("Something went wrong... Please try after sometime");

        }
    }

    @GetMapping
    public List<OrderRequest> getAllOrders() {
        return orderService.getAllOrders();
    }

    public ResponseEntity<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return ResponseEntity.ok("Oops something went wrong , Please try again...");
    }
}
