package com.mall.server.exception.client;

import com.mall.server.exception.ExceptionCode;

/**
 * 요청에 대한 해독에 문제가 있을 때 발생하는 예외
 */
public class WrongPresentationRequestException extends ClientException {
    public WrongPresentationRequestException(ExceptionCode code, Throwable cause) {
        super(code, cause);
    }
}
