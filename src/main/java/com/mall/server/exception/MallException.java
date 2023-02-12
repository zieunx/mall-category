package com.mall.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.text.MessageFormat;
import java.util.List;

/**
 * mall 전체에서 발생하는 모든 예외의 최상위 예외
 */
@Getter
public class MallException extends RuntimeException {
    private String message;
    private HttpStatus status;
    private String code;
    private List<ObjectError> errors;

    public MallException(ExceptionCode code, Throwable cause) {
        super(code.getMessage(), cause);
        init(code);
    }

    public MallException(ExceptionCode code, Throwable cause, List<ObjectError> errors) {
        super(code.getMessage(), cause);
        init(code);
        this.errors = errors;
    }

    public MallException(ExceptionCode code, Object... args) {
        super(MessageFormat.format(code.getMessage(), args));
        init(code);
        this.message = MessageFormat.format(code.getMessage(), args);
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    private void init(ExceptionCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
}
