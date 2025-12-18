package com.achraf.customersvc.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "emailOnly", types = Customer.class)
public interface CustomerProjectionEmail {
    String getEmail();
}