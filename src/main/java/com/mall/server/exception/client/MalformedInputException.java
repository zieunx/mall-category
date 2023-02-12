package com.mall.server.exception.client;

import com.mall.server.exception.ExceptionCode;

/**
 * 내용 해독 불가능한 입력을 받을 때 발생시킬 예외
 */
public class MalformedInputException extends ClientException {

    public MalformedInputException(ExceptionCode code, Throwable cause) {
        super(code, cause);
    }
}
