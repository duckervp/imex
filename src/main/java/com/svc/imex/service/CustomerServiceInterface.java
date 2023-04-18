package com.svc.imex.service;

import com.svc.imex.domain.entity.Customer;

import java.util.List;

public interface CustomerServiceInterface {
    List<Customer> findAll();

    void saveAll();
}
