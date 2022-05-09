package nl.bd.eindopdrachtjava.security;

import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) userDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
        return userDetails;
    }
}
