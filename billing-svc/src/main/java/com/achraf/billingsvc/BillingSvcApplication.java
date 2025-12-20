package com.achraf.billingsvc;

import com.achraf.billingsvc.entities.Bill;
import com.achraf.billingsvc.entities.ProductItem;
import com.achraf.billingsvc.feign.CustomerRestClient;
import com.achraf.billingsvc.feign.ProductRestClient;
import com.achraf.billingsvc.model.Customer;
import com.achraf.billingsvc.model.Product;
import com.achraf.billingsvc.repository.BillRepository;
import com.achraf.billingsvc.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingSvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository,
                                        ProductItemRepository productItemRepository,
                                        CustomerRestClient customerRestClient,
                                        ProductRestClient productRestClient) {
        return args -> {
            Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
            Collection<Product> products = productRestClient.getAllProducts().getContent();

            customers.forEach(customer -> {
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(customer.getId())
                        .build();
                billRepository.save(bill);

                products.forEach(product -> {
                    ProductItem productItem = ProductItem.builder()
                            .bill(bill)
                            .productId(product.getId())
                            .unitPrice(product.getPrice())
                            .quantity(1 + (int)(Math.random() * 10))
                            .build();
                    productItemRepository.save(productItem);
                });
            });
        };
    }
}
