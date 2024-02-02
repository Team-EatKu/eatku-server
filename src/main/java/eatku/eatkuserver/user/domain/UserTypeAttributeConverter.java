package eatku.eatkuserver.user.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter
public class UserTypeAttributeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType attribute) {
        return attribute.getType();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(UserType.class).stream()
                .filter(e->e.getType().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
