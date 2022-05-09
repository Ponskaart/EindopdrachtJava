package nl.bd.eindopdrachtjava.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    EMPLOYEE(Code.EMPLOYEE),
    ADMIN(Code.ADMIN),
    CUSTOMER(Code.CUSTOMER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    public class Code {
        public static final String EMPLOYEE = "ROLE_EMPLOYEE";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String CUSTOMER = "ROLE_CUSTOMER";
    }
}