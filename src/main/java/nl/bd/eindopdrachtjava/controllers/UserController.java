package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.AdminAuthorization;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Contains endpoints for all user related functions.
 */
@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Creates a user and registers it in the database.
     */
    @AdminAuthorization
    @PostMapping("/add/user")
    public User registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userService.registerUser(userRegistrationRequest);
    }

    /**
     * Updates an existing user.
     */
    @AdminAuthorization
    @PutMapping("/update/user/{userId}")
    public User updateUser(@RequestBody UserRegistrationRequest userRegistrationRequest,
                           @PathVariable Long userId) {
        return userService.updateUser(userRegistrationRequest,userId);
    }

    /**
     * Returns the user details of a specific user.
     */
    @AdminAuthorization
    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

    /**
     * Deletes a specific user.
     */
    @AdminAuthorization
    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}