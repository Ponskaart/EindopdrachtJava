package nl.bd.eindopdrachtjava.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO  Load the user from the users table by username. If not found, throw UsernameNotFoundException.
        // Convert/wrap the user to a UserDetails object and return it.
        return someUserDetails;
    }
}
