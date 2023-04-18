package com.svc.imex.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportServiceInterface {
    void importCustomer(MultipartFile file, boolean containsHeader) throws IOException;
}
