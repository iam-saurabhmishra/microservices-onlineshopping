package com.onlineshopping.orderservice.dto;

import com.onlineshopping.orderservice.model.OrderLineItems;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
