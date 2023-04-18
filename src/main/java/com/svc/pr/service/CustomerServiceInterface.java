package com.svc.pr.service;

import com.svc.pr.domain.entity.Customer;

import java.util.List;

public interface CustomerServiceInterface {
    List<Customer> findAll();

    void saveAll();
}
