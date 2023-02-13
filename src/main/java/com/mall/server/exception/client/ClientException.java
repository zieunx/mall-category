package com.mall.server.exception.client;

import com.mall.server.exception.ExceptionCode;
import com.mall.server.exception.MallException;

import java.util.List;

/**
 * 잘못되거나 지원할 수 없는 요청을 했을 때 발생하는 예외의 상위 예외
 */
public class ClientException extends MallException {

    public ClientException(ExceptionCode code, Throwable cause) {
        super(code, cause);
    }

    public ClientException(ExceptionCode code, Throwable cause, List<String> errors) {
        super(code, cause, errors);
    }

    public ClientException(ExceptionCode code, Object... args) {
        super(code, args);
    }
}
