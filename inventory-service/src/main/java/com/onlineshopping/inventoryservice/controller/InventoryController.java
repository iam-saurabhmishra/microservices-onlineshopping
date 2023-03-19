package com.onlineshopping.inventoryservice.controller;

import com.onlineshopping.inventoryservice.dto.InventoryResponse;
import com.onlineshopping.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {


   private final InventoryService inventoryServic;

   @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

       return inventoryServic.isInStock(skuCode);
    }
}
