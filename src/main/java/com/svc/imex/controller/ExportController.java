package com.svc.imex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.svc.imex.service.ExportServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("exports/")
public class ExportController {
    private final ExportServiceInterface exportService;

    @GetMapping
    public Object exportCustomer(@RequestParam(required = false) String dateFrom,
                                 @RequestParam(required = false) String dateTo,
                                 @RequestParam(required = false, defaultValue = "10") Long limit,
                                 @RequestParam(required = false, defaultValue = "CSV") String deliveryMethod) throws JsonProcessingException {
        return exportService.exportCustomer(dateFrom, dateTo, limit, deliveryMethod);
    }
}
