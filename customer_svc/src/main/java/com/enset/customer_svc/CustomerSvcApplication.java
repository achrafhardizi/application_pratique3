package com.enset.customer_svc;

import com.enset.customer_svc.models.Customer;
import com.enset.customer_svc.repositories.CustomerRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerSvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepositories customerRepositories) {
        return args -> {
            customerRepositories.save(Customer.builder()
                    .name("Mohammed")
                    .email("med@gmail.com")
                    .build());
            customerRepositories.save(Customer.builder()
                    .name("Imane")
                    .email("imane@gmail.com")
                    .build());
            customerRepositories.save(Customer.builder()
                    .name("Yassine")
                    .email("yassine@gmail.com")
                    .build());
            customerRepositories.findAll().forEach(c->{
                System.out.println("=============================");
                System.out.println(c.getId());
                System.out.println(c.getName());
                System.out.println(c.getEmail());
                System.out.println("=============================");
            });
        };
    }
}
