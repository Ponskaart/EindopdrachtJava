package nl.bd.eindopdrachtjava.models.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * Custom annotation to enable easy authorization for users
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority(T(nl.bd.eindopdrachtjava.models.enums.UserRole).EMPLOYEE) " +
        "or hasAuthority(T(nl.bd.eindopdrachtjava.models.enums.UserRole).ADMIN)")
public @interface AdminAndEmployeeAuthorization {
}
