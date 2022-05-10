package nl.bd.eindopdrachtjava.security;

import lombok.Getter;

@Getter
public enum UserPermissions {
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }
}
