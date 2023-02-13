package com.mall.server.exception.client;

import com.mall.server.exception.ExceptionCode;

/**
 * 요청을 처리할 대상을 못찾았을 때 발생하는 예외
 */
public class RequestedServiceNotFoundException extends ClientException {

    public RequestedServiceNotFoundException(ExceptionCode code, Throwable cause) {
        super(code, cause);
    }
}
