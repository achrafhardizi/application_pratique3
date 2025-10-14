package com.enset.customer_svc.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "email", types = Customer.class)
public interface CustomerProjectionEmail {
    String getEmail();
}
