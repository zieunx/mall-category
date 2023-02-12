package com.mall.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Business logic 예외 코드 정의
 */
@Getter
public enum ExceptionCode {
    // 비즈니스 독립적인 일반 예외 코드
    UNHANDLED_EXCEPTION("E0001", "요청을 처리하던 중 알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_NOT_FOUND("E0002", "요청을 처리할 서비스가 없습니다.", HttpStatus.NOT_FOUND),
    WRONG_REQUEST("E0003", "입력 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    MALFORMED_INPUT("E0004", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    WRONG_PRESENTATION("E0005", "지원하지 않는 미디어 타입 입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    // 특정 비즈니스 종속 예외 코드
    CATEGORY_NOT_FOUND("EC0001", "카테고리 ''{0}''를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private String code;
    private String message;
    private HttpStatus status;

    ExceptionCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
