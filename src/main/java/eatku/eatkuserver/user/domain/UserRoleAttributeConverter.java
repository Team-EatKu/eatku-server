package eatku.eatkuserver.user.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

@Converter
public class UserRoleAttributeConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute.getRole();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(UserRole.class).stream()
                .filter(e->e.getRole().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
