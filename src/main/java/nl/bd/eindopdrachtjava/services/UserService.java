package nl.bd.eindopdrachtjava.services;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.exceptions.ResourceAlreadyExistsException;
import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageService messageService;

    /**
     * Loads user from the database if username exists.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageService.userNotFound()));
    }

    /**
     * Registers new user in the database.
     */
    public User registerUser(UserRegistrationRequest userRegistrationRequest) throws ResourceAlreadyExistsException {
        if (userExists(userRegistrationRequest)) {
            throw new ResourceAlreadyExistsException(
                    messageService.userAlreadyExists(userRegistrationRequest.getUsername()));
        }

        User user = createUser(userRegistrationRequest);
        return userRepository.save(user);
    }

    /**
     * Checks if User with given Id exists and updates user.
     */
    public User updateUser(UserRegistrationRequest userRegistrationRequest, Long userId)
            throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> updatedUser(userRegistrationRequest, user))
                .orElseThrow(() -> new ResourceNotFoundException(messageService.userIdNotFound(userId)));
    }

    /**
     * Deletes user with given Id from database.
     */
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(messageService.userIdNotFound(userId));
        }
        userRepository.deleteById(userId);
    }

    /**
     * Checks if user with given username exists
     */
    private boolean userExists(UserRegistrationRequest userRegistrationRequest) {
        return userRepository.findByUsername(userRegistrationRequest.getUsername()).isPresent();
    }

    /**
     * Creates user to use in the registerUser method.
     */
    private User createUser(UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .username(userRegistrationRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()))
                .role(userRegistrationRequest.getRole())
                .build();
    }

    /**
     * Checks if userRegistrationRequest has empty fields, and uses the old values where no new values were given.
     */
    private User updatedUser(UserRegistrationRequest userRegistrationRequest, User user) {
        if (userRegistrationRequest.getUsername() != null) {
            user.setUsername(userRegistrationRequest.getUsername());
        }
        if (userRegistrationRequest.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()));
        }
        if (userRegistrationRequest.getRole() != null) {
            user.setRole(userRegistrationRequest.getRole());
        }

        return user;
    }
}
