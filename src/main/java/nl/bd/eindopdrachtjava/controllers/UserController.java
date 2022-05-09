package nl.bd.eindopdrachtjava.controllers;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import nl.bd.eindopdrachtjava.security.MyUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recordstore")
@AllArgsConstructor
public class UserController {
    private final UserDetailsRepository userDetailsRepository;
    private final MyUserDetailsService myUserDetailsService;

    @PostMapping("/add/user")
    public User registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return myUserDetailsService.registerUser(userRegistrationRequest);
    }
}
