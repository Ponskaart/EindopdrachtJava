package nl.bd.eindopdrachtjava.services;

import nl.bd.eindopdrachtjava.exceptions.ResourceNotFoundException;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    /**
     * Loads user from the database if username exists.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) userDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
        return userDetails;
    }

    /**
     * Registers new user in the database.
     */
    public User registerUser(UserRegistrationRequest userRegistrationRequest){
        User user = createUser(userRegistrationRequest);
        return userDetailsRepository.save(user);
    }

    /**
     * Checks if User with given Id exists and updates user.
     */
    public User updateUser(UserRegistrationRequest userRegistrationRequest, Long userId) throws ResourceNotFoundException {
        return userDetailsRepository.findById(userId).map(user -> updatedUser(userRegistrationRequest, user))
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " was not found" ));

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
            user.setUsername(userDetailsRepository.findById(tempUserId).get().getUsername());
        } else {
            user.setUsername(userRegistrationRequest.getUsername());
        }

        if (userRegistrationRequest.getPassword() == null) {
            user.setPassword(userDetailsRepository.findById(tempUserId).get().getPassword());
        } else {
            user.setPassword(userRegistrationRequest.getPassword());
        }

        if (userRegistrationRequest.getRole() == null) {
            user.setRole(userDetailsRepository.findById(tempUserId).get().getRole());
        } else {
            user.setRole(userRegistrationRequest.getRole());
        }

        return user;
    }
}
