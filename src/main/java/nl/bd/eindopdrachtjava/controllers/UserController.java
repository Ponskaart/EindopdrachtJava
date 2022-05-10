package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import nl.bd.eindopdrachtjava.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class UserController {
    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;

    @PostMapping("/add/user")
    public User registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return userService.registerUser(userRegistrationRequest);
    }

    @GetMapping("/getadmin")
    public User GetAdmin() {
        return userDetailsRepository.findById(1L).get();
    }
}
