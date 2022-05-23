package nl.bd.eindopdrachtjava.models.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enums to differentiate the different roles in the system.
 */
public enum UserRole {
    EMPLOYEE(Sets.newHashSet()),
    ADMIN(Sets.newHashSet()),
    CUSTOMER(Sets.newHashSet());

    /**
     * Set of granted permission for the user
     */
    private final Set<UserPermissions> permissions;

    UserRole(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return this.permissions;
    }

    /**
     * Fills set with permissions, although IDE does not seem to directly use this method, it seems necessary for the
     * rest of the class to function properly. For without it, the permissions Sets would never be filled and the enums
     * then cannot function.
     */
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}