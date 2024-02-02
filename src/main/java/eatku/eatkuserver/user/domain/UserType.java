package eatku.eatkuserver.user.domain;

public enum UserType {
    ADMIN("ADMIN", "관리자"), USER("USER", "일반");

    private String type;
    private String value;

    UserType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
