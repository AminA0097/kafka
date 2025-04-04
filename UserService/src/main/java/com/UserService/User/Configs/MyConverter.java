package com.UserService.User.Configs;
import jakarta.persistence.AttributeConverter;


public class MyConverter implements AttributeConverter<Boolean,Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? 1 : 0;
    }
    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return dbData != null && dbData == 1;}
}
