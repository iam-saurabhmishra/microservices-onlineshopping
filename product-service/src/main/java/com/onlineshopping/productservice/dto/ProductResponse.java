package com.onlineshopping.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Slf4j
public class ProductResponse {
    private String productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
}
