package com.svc.pr.repository;

import com.svc.pr.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    boolean existsByMobileNo(String mobileNo);

}
