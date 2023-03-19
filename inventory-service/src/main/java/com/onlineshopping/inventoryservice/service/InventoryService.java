package com.onlineshopping.inventoryservice.service;

import com.onlineshopping.inventoryservice.dto.InventoryResponse;
import com.onlineshopping.inventoryservice.model.Inventory;
import com.onlineshopping.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory -> InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0).build()
                ).toList();
    }
}
