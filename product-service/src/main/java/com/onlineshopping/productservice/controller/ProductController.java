package com.onlineshopping.productservice.controller;

import com.onlineshopping.productservice.dto.ProductRequest;
import com.onlineshopping.productservice.dto.ProductResponse;
import com.onlineshopping.productservice.model.Product;
import com.onlineshopping.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    final ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
       productService.createProduct(productRequest);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
     public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
     }
}
