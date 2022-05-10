package nl.bd.eindopdrachtjava.services;

import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user from the database if username exists.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
        return userDetails;
    }

    /**
     * Registers new user in the database.
     */
    public User registerUser(UserRegistrationRequest userRegistrationRequest){
        User user = createUser(userRegistrationRequest);
        return userRepository.save(user);
    }

    /**
     * Checks if User with given Id exists and updates user.
     */
    public User updateUser(UserRegistrationRequest userRegistrationRequest, Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> updatedUser(userRegistrationRequest, user))
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " was not found" ));

    }

    /**
     * Deletes user with given Id from database.
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Creates user to use in the registerUser method.
     */
    private User createUser(UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .username(userRegistrationRequest.getUsername())
                .password(userRegistrationRequest.getPassword())
                .role(userRegistrationRequest.getRole())
                .build();
    }

    /**
     * Checks if userRegistrationRequest has empty fields, and uses the old values where no new values were given.
     */
    private User updatedUser(UserRegistrationRequest userRegistrationRequest, User user) {
        Long tempUserId = user.getUserId();

        if (userRegistrationRequest.getUsername() == null) {
            user.setUsername(userRepository.findById(tempUserId).get().getUsername());
        } else {
            user.setUsername(userRegistrationRequest.getUsername());
        }

        if (userRegistrationRequest.getPassword() == null) {
            user.setPassword(userRepository.findById(tempUserId).get().getPassword());
        } else {
            user.setPassword(userRegistrationRequest.getPassword());
        }

        if (userRegistrationRequest.getRole() == null) {
            user.setRole(userRepository.findById(tempUserId).get().getRole());
        } else {
            user.setRole(userRegistrationRequest.getRole());
        }

        return user;
    }
}
