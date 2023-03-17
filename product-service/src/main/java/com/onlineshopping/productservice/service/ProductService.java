package com.onlineshopping.productservice.service;

import com.onlineshopping.productservice.dto.ProductRequest;
import com.onlineshopping.productservice.dto.ProductResponse;
import com.onlineshopping.productservice.model.Product;
import com.onlineshopping.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product= Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productPrice(productRequest.getProductPrice())
                .build();
        productRepository.save(product);
        log.info("Product added with id : {} and name = {} ",product.getProductId(),product.getProductName());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public Optional<Product> getProduct(String productId){
        return productRepository.findById(productId);
    }
    private  ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice()).build();
    }
}
