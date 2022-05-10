package nl.bd.eindopdrachtjava.models.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;

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

    public Set<UserPermissions> getPermissions(){
        return this.permissions;
    }

    /**
     * Fills set with permissions, although IDE does not seem to use method, I am reluctant to remove it.
     */
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}