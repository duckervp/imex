package com.svc.imex.common;

import com.svc.imex.domain.entity.Gender;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Utils {
    public static LocalDateTime parseDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            try  {
                result = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e2) {
                try {
                    result = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"));
                } catch (Exception ee) {
                    try {
                        result = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
                    } catch (Exception e3) {
                        try {
                            result = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        } catch (Exception e4) {
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                                Date date1 = simpleDateFormat.parse(date);
                                result = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
                            } catch (Exception e5) {
                                throw new RuntimeException(e5);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static String validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            return null;
        }
        return email;
    }


    public static void main(String[] args) {
        Gender gender = Gender.valueOf("");
        System.out.println(gender);
    }

}
