package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.AdminAuthorization;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.services.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * Contains endpoints for all user related functions.
 */
@RestController
@RequestMapping("recordstore/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Creates a user and registers it in the database.
     */
    @AdminAuthorization
    @PostMapping("/add")
    public User registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userService.registerUser(userRegistrationRequest);
    }

    /**
     * Returns the user details of a specific user.
     */
    @AdminAuthorization
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

    /**
     * Updates an existing user.
     */
    @AdminAuthorization
    @PutMapping("/update/{userId}")
    public User updateUser(@RequestBody UserRegistrationRequest userRegistrationRequest,
                           @PathVariable Long userId) {
        return userService.updateUser(userRegistrationRequest,userId);
    }

    /**
     * Deletes a specific user.
     */
    @AdminAuthorization
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}