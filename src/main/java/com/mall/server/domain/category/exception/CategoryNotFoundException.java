package com.mall.server.domain.category.exception;

import com.mall.server.exception.client.ClientException;

import static com.mall.server.exception.ExceptionCode.CATEGORY_NOT_FOUND;

/**
 *
 */
public class CategoryNotFoundException extends ClientException {

    public CategoryNotFoundException(Object... args) {
        super(CATEGORY_NOT_FOUND, args);
    }
}
