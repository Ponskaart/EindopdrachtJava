package nl.bd.eindopdrachtjava.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    EMPLOYEE(UserRoleString.EMPLOYEE),
    ADMIN(UserRoleString.ADMIN),
    CUSTOMER(UserRoleString.CUSTOMER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public static class UserRoleString {
        public static final String EMPLOYEE = "ROLE_EMPLOYEE";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String CUSTOMER = "ROLE_CUSTOMER";
    }
}