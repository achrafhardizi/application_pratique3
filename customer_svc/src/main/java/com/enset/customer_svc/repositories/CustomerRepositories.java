package com.enset.customer_svc.repositories;

import com.enset.customer_svc.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepositories extends JpaRepository<Customer, Long> {
}
