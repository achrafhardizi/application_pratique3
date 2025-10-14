package com.enset.inventory_svc.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
}
