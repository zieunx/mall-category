package com.mall.server.exception.server;

import com.mall.server.exception.ExceptionCode;
import com.mall.server.exception.MallException;

/**
 * 서비스 내부의 문제로 인해 요청을 처리할 수 없을 때 발생시키는 예외의 상위 예외
 */
public class ServerException extends MallException {
    public ServerException(ExceptionCode code, Object... args) {
        super(code, args);
    }
}
