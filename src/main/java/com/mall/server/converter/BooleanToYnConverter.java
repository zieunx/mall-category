package com.mall.server.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 코드에서 Boolean 으로 사용하고 DB 에서 String 으로 사용하기 위한 Converter 입니다.
 */
@Converter
public class BooleanToYnConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
