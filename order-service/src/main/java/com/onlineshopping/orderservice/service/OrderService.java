package com.onlineshopping.orderservice.service;

import com.onlineshopping.orderservice.dto.OrderLineItemsDto;
import com.onlineshopping.orderservice.dto.OrderRequest;
import com.onlineshopping.orderservice.model.Order;
import com.onlineshopping.orderservice.model.OrderLineItems;
import com.onlineshopping.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder (OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
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
