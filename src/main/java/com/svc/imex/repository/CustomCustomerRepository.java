package com.svc.imex.repository;

import com.svc.imex.domain.entity.Customer;
import com.svc.imex.service.ExportServiceInterface;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class CustomCustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Object findAll(LocalDateTime dateFrom, LocalDateTime dateTo, Long limit, String deliveryMethod) {
        StringBuilder sql = new StringBuilder("SELECT customerid, firstname, lastname, birthdate, email, mobileno, ");
        sql.append("address, gender, status, createdate, lastmoddate FROM customer WHERE 1 = 1 ");
        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);

        if (Objects.nonNull(dateFrom)) {
            sql.append("AND createdate >= :dateFrom ");
            params.put("dateFrom", dateFrom);
        }

        if (Objects.nonNull(dateTo)) {
            sql.append("AND createdate < :dateTo ");
            params.put("dateTo", dateTo);
        }

        sql.append("LIMIT :limit");

        Query query;

        if (deliveryMethod.equals(ExportServiceInterface.CSV)) {
            query = entityManager.createNativeQuery(sql.toString());
        } else {
            query = entityManager.createNativeQuery(sql.toString(), Customer.class);
        }

        params.forEach(query::setParameter);

        return query.getResultList();
    }
}
