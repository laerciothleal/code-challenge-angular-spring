package com.backend.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private Integer status;
    private String code;
    private String message;
    private Collection<String> validationErrors;
    private String[] stackTrace;

}
 