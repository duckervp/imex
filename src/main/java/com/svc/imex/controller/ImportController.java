package com.svc.imex.controller;

import com.svc.imex.common.RespMessage;
import com.svc.imex.common.Response;
import com.svc.imex.service.ImportServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("imports/")
public class ImportController {
    private final ImportServiceInterface importService;

    @PostMapping
    public ResponseEntity<?> importCustomer(@RequestParam("file") MultipartFile file,
                                            @RequestParam(required = false, defaultValue = "true") boolean containsHeader) throws IOException {
        importService.importCustomer(file, containsHeader);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), RespMessage.IMPORTED_CUSTOMER));
    }

}
