package com.mall.server.advice.errorhandler;

import com.mall.server.exception.*;
import com.mall.server.exception.client.ClientException;
import com.mall.server.exception.client.WrongPresentationRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import javax.servlet.ServletException;

/**
 * [ServletException] 처리 handler 입니다.
 *
 * Spring Context 밖에서 발생하는 경우가 많고 발생할 때 마다 추가 필요합니다.
 */
@Slf4j
@Component
public class ServletExceptionHandler implements ExceptionHandlerContract<ServletException> {
    @Override
    public ResponseEntity<ErrorResponse> onException(ServletException exception) {
        log.error("javax.servlet.ServletException: ", exception);

        ClientException e = null;
        if (exception instanceof HttpMediaTypeNotSupportedException) {
            e = new WrongPresentationRequestException(ExceptionCode.WRONG_PRESENTATION, exception);
        }

        return ResponseEntity
                .status(e != null ? e.getStatus() : HttpStatus.BAD_REQUEST)
                .body(e != null ? ErrorResponse.from(e) : ErrorResponse.from(ExceptionCode.MALFORMED_INPUT));
    }
}
