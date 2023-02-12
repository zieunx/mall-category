package com.mall.server.advice.errorhandler;

import com.mall.server.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandlerContract<T extends Exception> {
    ResponseEntity<ErrorResponse> onException(T exception);
}
