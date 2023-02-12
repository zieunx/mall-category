package com.mall.server.exception.client;

import com.mall.server.exception.ExceptionCode;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 내용은 해독했지만, 문맥이나 타입에 맞지 않는 입력을 받았을 때 발생시킬 예외
 */
public class WrongInputException extends ClientException {

    public WrongInputException(ExceptionCode code, Throwable cause, List<ObjectError> errors) {
        super(code, cause, errors);
    }
}
