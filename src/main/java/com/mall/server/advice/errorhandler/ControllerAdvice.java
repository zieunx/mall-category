package com.mall.server.advice.errorhandler;

import com.mall.server.exception.ErrorResponse;
import com.mall.server.exception.ExceptionCode;
import com.mall.server.exception.MallException;
import com.mall.server.exception.client.MalformedInputException;
import com.mall.server.exception.client.WrongInputException;
import com.mall.server.exception.server.UnhandledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 어플리케이션 내에서 발생하는 모든 예외를 [ErrorResponse] 타입으로 변경하는 공통 로직
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {
    private final ExceptionHandlerContract<MallException> mallExceptionHandler;
    private final ExceptionHandlerContract<ServletException> servletExceptionHandler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> onException(Exception exception) {
        if (exception instanceof HttpMessageConversionException) {
            return mallExceptionHandler.onException(
                    new MalformedInputException(ExceptionCode.MALFORMED_INPUT, exception)
            );
        } else if (exception instanceof TypeMismatchException) {
            return mallExceptionHandler.onException(
                    new WrongInputException(ExceptionCode.WRONG_REQUEST, exception)
            );
        }

        return onUnhandledException(exception);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> onServletException(ServletException exception) {
        return servletExceptionHandler.onException(exception);
    }

    @ExceptionHandler(MallException.class)
    public ResponseEntity<ErrorResponse> onMallException(MallException exception) {
        return mallExceptionHandler.onException(exception);
    }

    // 400
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> onBindException(BindException exception) {
        log.error("Bind Exception:", exception);
        List<String> errors = exception.getBindingResult().getAllErrors()
                .stream()
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(Collectors.toList());

        return mallExceptionHandler.onException(
                new WrongInputException(ExceptionCode.WRONG_REQUEST, exception, errors)
        );
    }

    // 500
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> onBindException(ConstraintViolationException exception) {
        log.error("Constraint Violation Exception:", exception);

        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> String.format("[%s] %s: %s", violation.getRootBeanClass().getSimpleName(), violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.toList());

        return mallExceptionHandler.onException(
                new WrongInputException(ExceptionCode.CONSTRAINT_VIOLATIONS, exception, errors)
        );
    }

    // 500
    private ResponseEntity<ErrorResponse> onUnhandledException(Exception exception) {
        log.error("Unhandled Exception:", exception);

        ErrorResponse error = ErrorResponse.from(
                new UnhandledException(ExceptionCode.UNHANDLED_EXCEPTION, exception)
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
