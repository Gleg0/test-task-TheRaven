package com.testtask.testtask.api.service;

import com.testtask.testtask.api.dao.CustomerRepository;
import com.testtask.testtask.api.exeption.ValidationException;
import com.testtask.testtask.api.model.Customer;
import com.testtask.testtask.api.model.CustomerEntity;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class CustomerService {

    String EMAIL = "^(?=.{1,100}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String FULL_NAME = "^(?=\\s*\\S)([\\s\\S]){2,50}$";
    String PHONE = "^\\+\\d{5,13}$";
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        return customerEntity.map(this::mapCustomerEntityToCustomer).orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAllByIsActiveIsTrue().stream().map(this::mapCustomerEntityToCustomer).toList();
    }

    public Customer saveOrUpdateCustomer(CustomerEntity updatedCustomer){
        if(!validationCustomer(updatedCustomer)){
            return null;
        }
        if(updatedCustomer.getId() == null){
            updatedCustomer.setCreated(Instant.now());
            updatedCustomer.setIsActive(true);
        }
        else {
            return null;
        }
        customerRepository.save(updatedCustomer);
        return mapCustomerEntityToCustomer(updatedCustomer);
    }

    public Customer saveOrUpdateCustomer(Long id,CustomerEntity updatedCustomer) {
        if(!validationCustomer(updatedCustomer)){
            return null;
        }
        if(customerRepository.findById(id).isPresent()){
            updatedCustomer.setId(id);
            updatedCustomer.setUpdated(Instant.now());
            updatedCustomer.setCreated(customerRepository.findById(id).get().getCreated());
            updatedCustomer.setIsActive(customerRepository.findById(id).get().getIsActive());
        }
        else
        {
            return null;
        }
        customerRepository.save(updatedCustomer);
        return mapCustomerEntityToCustomer(updatedCustomer);
    }

    public void deleteCustomer(Long id){
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            customer.get().setIsActive(false);
            customerRepository.save(customer.get());
        }
    }

    private Customer mapCustomerEntityToCustomer(CustomerEntity customerEntity){
        if(customerEntity == null)
            return null;
        else
            return new Customer(
                customerEntity.getId(),
                customerEntity.getFullName(),
                customerEntity.getEmail(),
                customerEntity.getPhone());
    }

    @SneakyThrows
    private boolean validationCustomer(CustomerEntity customerEntity){
        if(!customerEntity.getEmail().matches(EMAIL))
            throw new ValidationException("Invalid email.");
        if(!customerRepository.findByEmail(customerEntity.getEmail()).isEmpty())
            throw new ValidationException("Email already used.");
        if(!customerEntity.getFullName().matches(FULL_NAME))
            throw new ValidationException("Invalid full name.");
        if(!(customerEntity.getPhone() == null))
            if(!customerEntity.getPhone().matches(PHONE)){
                throw new ValidationException("Invalid phone number.");
            }
        return true;
    }
}
