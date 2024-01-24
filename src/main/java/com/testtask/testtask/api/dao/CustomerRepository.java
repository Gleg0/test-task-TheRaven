package com.testtask.testtask.api.dao;

import com.testtask.testtask.api.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    List<CustomerEntity> findAllByIsActiveIsTrue();
    List<CustomerEntity> findByEmail(String email);
}
