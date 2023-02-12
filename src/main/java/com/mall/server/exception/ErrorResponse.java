package com.mall.server.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 에러 공통 Response
 */
@Getter
public class ErrorResponse {
    private String message;
    private String code;
    private List<String> errors = new ArrayList<>();

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ErrorResponse(String message, String code, List<ObjectError> errors) {
        this.message = message;
        this.code = code;
        if (errors != null) {
            this.errors = errors.stream()
                    .map(error -> {
                        FieldError fieldError = (FieldError) error;
                        return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
                    })
                    .collect(Collectors.toList());
        }
    }

    public static ErrorResponse from(MallException exception) {
        return new ErrorResponse(exception.getMessage(), exception.getCode(), exception.getErrors());
    }

    public static ErrorResponse from(ExceptionCode code) {
        return new ErrorResponse(code.getMessage(), code.getCode());
    }
}
