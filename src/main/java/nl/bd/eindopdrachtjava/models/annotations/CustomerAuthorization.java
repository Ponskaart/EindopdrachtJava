package nl.bd.eindopdrachtjava.models.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority(T(nl.bd.eindopdrachtjava.models.enums.UserRole).CUSTOMER)")
public @interface CustomerAuthorization {
}
