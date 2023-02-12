package com.mall.server.advice.errorhandler;

import com.mall.server.exception.ErrorResponse;
import com.mall.server.exception.ExceptionCode;
import com.mall.server.exception.MallException;
import com.mall.server.exception.client.MalformedInputException;
import com.mall.server.exception.client.WrongInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

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

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> onBindException(BindException exception) {
        return mallExceptionHandler.onException(
                new WrongInputException(ExceptionCode.WRONG_REQUEST, exception, exception.getBindingResult().getAllErrors())
        );
    }

    private ResponseEntity<ErrorResponse> onUnhandledException(Exception exception) {
        log.error("Unhandled Exception:", exception);

        ErrorResponse error = ErrorResponse.from(ExceptionCode.UNHANDLED_EXCEPTION);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
