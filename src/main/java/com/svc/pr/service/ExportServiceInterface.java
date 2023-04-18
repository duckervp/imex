package com.svc.pr.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ExportServiceInterface {
    String CSV = "CSV";

    String API = "API";

    String JSON = "JSON";

    Object exportCustomer(String dateFrom, String dateTo, Long limit, String deliveryMethod) throws JsonProcessingException;
}
