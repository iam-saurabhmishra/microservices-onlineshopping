package com.onlineshopping.orderservice.service;

import com.onlineshopping.orderservice.dto.InventoryResponse;
import com.onlineshopping.orderservice.dto.OrderLineItemsDto;
import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.model.Order;
import com.onlineshopping.orderservice.model.OrderLineItems;
import com.onlineshopping.orderservice.model.ResponseEnum;
import com.onlineshopping.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public ResponseEnum placeOrder (OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();
        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(e->e.getSkuCode()).toList();
        //Call inventory service
        InventoryResponse[] skuCodeArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductIsInStock = Arrays.stream(skuCodeArray).allMatch(InventoryResponse::isInStock);
        if(allProductIsInStock) {
            orderRepository.save(order);
            log.info("Order placed successfully");
            return ResponseEnum.SUCCESS;
        }else {
            log.info("Few item ordered are not in stock");
            return ResponseEnum.NOT_IN_STOCK;
        }

    }

    public List<OrderRequest> getAllOrders() {
        List<Order> orders=orderRepository.findAll();
        List<OrderRequest> orderRequestList = new ArrayList<>();
        for (Order order : orders){
           OrderRequest orderRequest = new OrderRequest();
           orderRequest.setOrderLineItemsDtoList(order.getOrderLineItemsList().stream().map(orderLineItems -> {
               OrderLineItemsDto orderLineItemsDto= new OrderLineItemsDto();
               orderLineItemsDto.setId(orderLineItems.getId());
               orderLineItemsDto.setQuantity(orderLineItems.getQuantity());
               orderLineItemsDto.setPrice(orderLineItems.getPrice());
               orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
               return orderLineItemsDto;
           }).toList());
           orderRequestList.add(orderRequest);
        }
        return orderRequestList;
    }


    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
