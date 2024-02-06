package eatku.eatkuserver.user.domain;

public enum UserRole {
    ADMIN("ADMIN", "관리자"), USER("USER", "일반");

    private String role;
    private String value;

    UserRole(String role, String value) {
        this.role = role;
        this.value = value;
    }

    public String getRole() {
        return role;
    }

    public String getValue() {
        return value;
    }
}
