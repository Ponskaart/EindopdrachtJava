package nl.bd.eindopdrachtjava.models.enums;

import nl.bd.eindopdrachtjava.security.UserPermissions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.rowset.RowSetWarning;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;

public enum UserRole {
    EMPLOYEE(Sets.newHashSet()),
    ADMIN(Sets.newHashSet()),
    CUSTOMER(Sets.newHashSet());

    private final Set<UserPermissions> permissions;

    UserRole(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions(){
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }

//
//    public static class UserRoleString {
//        public static final String EMPLOYEE = "ROLE_EMPLOYEE";
//        public static final String ADMIN = "ROLE_ADMIN";
//        public static final String CUSTOMER = "ROLE_CUSTOMER";
//    }
}