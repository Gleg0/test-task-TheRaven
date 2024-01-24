package com.testtask.testtask.api.controller;

import com.testtask.testtask.api.model.Customer;
import com.testtask.testtask.api.model.CustomerEntity;
import com.testtask.testtask.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable("id") Long id){
        return customerService.getCustomer(id);
    }
    @GetMapping("/customers")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerEntity customer){
        return customerService.saveOrUpdateCustomer(id,customer);
    }
    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody CustomerEntity customer){
        return customerService.saveOrUpdateCustomer(customer);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
    }
}
