package com.svc.pr.service.impl;

import com.svc.pr.common.Utils;
import com.svc.pr.domain.entity.Customer;
import com.svc.pr.domain.entity.Gender;
import com.svc.pr.domain.entity.Status;
import com.svc.pr.domain.exception.ValidateException;
import com.svc.pr.repository.CustomerRepository;
import com.svc.pr.service.ImportServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService implements ImportServiceInterface {

    private final CustomerRepository customerRepository;

    @Override
    public void importCustomer(MultipartFile file, boolean containsHeader) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<Customer> customerToImport = new ArrayList<>();
        String line;
        boolean isFirstLine = true;
        while ((line = bufferedReader.readLine()) != null) {
            if (isFirstLine && containsHeader) {
                isFirstLine = false;
                continue;
            }
            try {
                Customer customer = validateLine(line);
                if (Objects.nonNull(customer)) {
                    customerToImport.add(customer);
                }
            } catch (Exception e) {
                log.info("Skip line: {}, error: {}", line, e.getMessage());
            }
        }
        bufferedReader.close();
        inputStream.close();
        if (!customerToImport.isEmpty()) {
            customerRepository.saveAll(customerToImport);
        }

    }

    private Customer validateLine(String line) {
        String[] parts = new String[20];
        String[] pieces = line.split(",");
        System.arraycopy(pieces, 0, parts, 0, pieces.length);

        String email = Utils.validateEmail(parts[3]);
        if (Objects.isNull(email)) {
            return null;
        }

        String firstName = parts[0];
        String lastName = parts[1];
        String mobileNo = parts[4];
        String address = parts[5];
        Gender gender = parts[6].isBlank() ? null : Gender.valueOf(parts[6]);
        Status status = parts[7].isBlank() ? null : Status.valueOf(parts[7]);
        LocalDateTime birthDate = StringUtils.isBlank(parts[2]) ? null :  Utils.parseDate(parts[2]);
        LocalDateTime createDate = StringUtils.isBlank(parts[8]) ? null :  Utils.parseDate(parts[8]);
        LocalDateTime lastModDate = StringUtils.isBlank(parts[9]) ? null :  Utils.parseDate(parts[9]);

        if (customerRepository.existsByMobileNo(mobileNo)) {
            throw new ValidateException("mobileNo is existed!");
        }

        Customer customer = customerRepository.findByEmail(email);


        if (Objects.nonNull(customer)) {
            if (!firstName.isEmpty()) {
                customer.setFirstName(firstName);
            }
            if (!lastName.isEmpty()) {
                customer.setLastName(lastName);
            }
            if (Objects.nonNull(birthDate)) {
                customer.setBirthDate(birthDate);
            }
            if (!mobileNo.isEmpty()) {
                customer.setMobileNo(mobileNo);
            }
            if (!address.isEmpty()) {
                customer.setAddress(address);
            }
            if (Objects.nonNull(gender)) {
                customer.setGender(gender);
            }
            if (Objects.nonNull(status)) {
                customer.setStatus(status);
            }
            customer.setLastModDate(LocalDateTime.now());
        } else {
            customer = Customer.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .birthDate(birthDate)
                    .email(email)
                    .mobileNo(mobileNo)
                    .address(address)
                    .gender(gender)
                    .status(status)
                    .build();

            if (Objects.isNull(createDate)) {
                createDate = LocalDateTime.now();
            }
            customer.setCreateDate(createDate);

            if (Objects.isNull(lastModDate)) {
                lastModDate = LocalDateTime.now();
            }
            customer.setLastModDate(lastModDate);
        }

        return customer;
    }
}
