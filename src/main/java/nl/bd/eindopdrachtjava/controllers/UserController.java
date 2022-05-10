package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.annotations.AdminAuthorization;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @AdminAuthorization
    @PostMapping("/add/user")
    public User registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        return userService.registerUser(userRegistrationRequest);
    }

    @AdminAuthorization
    @PutMapping("/update/user/{userId}")
    public User updateUser(@RequestBody UserRegistrationRequest userRegistrationRequest, @PathVariable Long userId) {
        return userService.updateUser(userRegistrationRequest,userId);
    }
    //TODO Add update user
    //TODO Add delete user
    //TODO add get user(?)
}