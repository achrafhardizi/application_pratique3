package com.enset.inventory_svc;

import com.enset.inventory_svc.models.Product;
import com.enset.inventory_svc.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventorySvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                  .id(UUID.randomUUID().toString())
                  .name("Computer")
                  .price(3200.00)
                  .quantity(11)
                  .build()) ;
            productRepository.save(Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Printer")
                    .price(1210.00)
                    .quantity(10)
                    .build()) ;
            productRepository.save(Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Computer")
                    .price(2300.00)
                    .quantity(35)
                    .build()) ;

            productRepository.findAll().forEach(prd -> {
                System.out.println("=======================");
                System.out.println(prd.getId());
                System.out.println(prd.getName());
                System.out.println(prd.getPrice());
                System.out.println(prd.getQuantity());
                System.out.println("=======================");
            });
        };
    }
}
