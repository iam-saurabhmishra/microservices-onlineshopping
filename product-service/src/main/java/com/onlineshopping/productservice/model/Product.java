package com.onlineshopping.productservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {

    @Id
    private String productId;
    private String productName;
    private String productDescription;
    private Double productPrice;

}
