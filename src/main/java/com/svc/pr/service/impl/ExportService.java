package com.svc.pr.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.svc.pr.common.RespMessage;
import com.svc.pr.common.Response;
import com.svc.pr.common.Utils;
import com.svc.pr.domain.entity.Customer;
import com.svc.pr.repository.CustomCustomerRepository;
import com.svc.pr.service.ExportServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExportService implements ExportServiceInterface {

    private final ObjectMapper objectMapper;

    private final CustomCustomerRepository customerRepository;

    @Override
    public Object exportCustomer(String dateFrom, String dateTo, Long limit, String deliveryMethod) throws JsonProcessingException {
        LocalDateTime createDateFrom = Utils.parseDate(dateFrom);
        LocalDateTime createDateTo = Utils.parseDate(dateTo);

        Object object = customerRepository.findAll(createDateFrom, createDateTo, limit,deliveryMethod);
        if (Arrays.asList(API, JSON).contains(deliveryMethod)) {
            @SuppressWarnings("unchecked")
            List<Customer> customers = (List<Customer>) object;

            if (deliveryMethod.equals(API)) {
                return ResponseEntity.ok(new Response(HttpStatus.OK.value(), RespMessage.EXPORTED_CUSTOMER, customers));
            } else {
                ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
                return ow.writeValueAsString(customers);
            }
        } else { // CSV
            StringBuilder stringBuilder = new StringBuilder("customerId,firstName,lastName,birthDate,email,mobileNo,");
            stringBuilder.append("address,gender,status,createDate,lastModDate\n");
            @SuppressWarnings("unchecked")
            List<Object[]> customers = (List<Object[]>) object;
            for (Object[] objects : customers) {
                stringBuilder.append(Stream.of(objects).map(o -> {
                    if (Objects.isNull(o)) {
                        return "";
                    } else {
                        return o.toString();
                    }
                }).collect(Collectors.joining(","))).append("\n");
            }
            return stringBuilder.toString();
        }
    }
}
