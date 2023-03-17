package com.onlineshopping.inventoryservice;

import com.onlineshopping.inventoryservice.model.Inventory;
import com.onlineshopping.inventoryservice.repository.InventoryRepository;
import com.onlineshopping.inventoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {

		return args -> {
			List<Inventory> inventories = inventoryRepository.findAll();
			if(inventories.size() == 0) {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("Iphone_13");
				inventory.setQuantity(10);

				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("Samsung7A");
				inventory1.setQuantity(5);

				inventoryRepository.save(inventory);
				inventoryRepository.save(inventory1);
			}

		};
	}
}
