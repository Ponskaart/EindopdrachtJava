package nl.bd.eindopdrachtjava.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.bd.eindopdrachtjava.models.enums.UserRole;

/**
 * Wrapper class to easily hand 3 variables to the registerUser method.
 */
@AllArgsConstructor
@Getter
public class UserRegistrationRequest {
    private final UserRole role;
    private final String username;
    private final String password;
}
