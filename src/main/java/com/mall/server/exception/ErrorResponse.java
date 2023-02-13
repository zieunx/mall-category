package com.mall.server.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    public ErrorResponse(String message, String code, List<String> errors) {
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public static ErrorResponse from(MallException exception) {
        return new ErrorResponse(exception.getMessage(), exception.getCode(), exception.getErrors());
    }

    public static ErrorResponse from(ExceptionCode code) {
        return new ErrorResponse(code.getMessage(), code.getCode());
    }
}
