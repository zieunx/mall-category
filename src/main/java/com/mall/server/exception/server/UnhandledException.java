package com.mall.server.exception.server;

import com.mall.server.exception.ExceptionCode;

public class UnhandledException extends ServerException {

    public UnhandledException(ExceptionCode code, Throwable cause) {
        super(code, cause);
    }
}
