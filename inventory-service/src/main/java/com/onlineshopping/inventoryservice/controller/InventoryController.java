package com.onlineshopping.inventoryservice.controller;

import com.onlineshopping.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {


   private final InventoryService inventoryServic;

   @GetMapping("/{skuCode}")
    public boolean isInStock(@PathVariable("skuCode") String skuCode){
       return inventoryServic.isInStock(skuCode);
    }
}
