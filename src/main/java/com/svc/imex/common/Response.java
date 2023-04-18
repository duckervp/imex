package com.svc.imex.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int code;
    private String message;
    private List<?> results;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
