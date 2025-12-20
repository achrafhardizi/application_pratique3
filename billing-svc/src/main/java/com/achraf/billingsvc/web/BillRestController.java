package com.achraf.billingsvc.web;


import com.achraf.billingsvc.entities.Bill;
import com.achraf.billingsvc.feign.CustomerRestClient;
import com.achraf.billingsvc.feign.ProductRestClient;
import com.achraf.billingsvc.repository.BillRepository;
import com.achraf.billingsvc.repository.ProductItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BillRestController {
    BillRepository billRepository;
    ProductItemRepository productItemRepository;
    CustomerRestClient customerRestClient;
    ProductRestClient productRestClient;

    @GetMapping("/bills/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(pi->{
            pi.setProducts(productRestClient.getProductById(pi.getProductId().toString()));
        });
        return bill;
    }
}
