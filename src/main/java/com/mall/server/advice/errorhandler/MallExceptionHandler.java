package com.mall.server.advice.errorhandler;

import com.mall.server.exception.client.ClientException;
import com.mall.server.exception.ErrorResponse;
import com.mall.server.exception.MallException;
import com.mall.server.exception.server.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * [MallException] 처리 handler 입니다.
 */
@Slf4j
@Component
public class MallExceptionHandler implements ExceptionHandlerContract<MallException> {

    @Override
    public ResponseEntity<ErrorResponse> onException(MallException exception) {
        if ((exception instanceof ClientException)) {
            return onClientException((ClientException) exception);
        }
        return onServerException((ServerException) exception);
    }

    private ResponseEntity<ErrorResponse> onClientException(ClientException exception) {
        log.info("Client Exception: ", exception);

        ErrorResponse error = ErrorResponse.from(exception);

        return ResponseEntity
                .status(exception.getStatus())
                .body(error);
    }

    private ResponseEntity<ErrorResponse> onServerException(ServerException exception) {
        log.info("Server Exception: ", exception);

        ErrorResponse error = ErrorResponse.from(exception);

        return ResponseEntity
                .status(exception.getStatus())
                .body(error);
    }
}
