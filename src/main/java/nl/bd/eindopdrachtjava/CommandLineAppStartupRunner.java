package nl.bd.eindopdrachtjava;

import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public void run(String...args) throws Exception {
        User admin = new User("Admin", "VeryGoodPassword", UserRole.ADMIN);
        userDetailsRepository.save(admin);
    }
}
