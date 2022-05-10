package nl.bd.eindopdrachtjava;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class runs before actual program starts and loads database with an admin user.
 */
@Configuration
@AllArgsConstructor
public class CommandLineAppStartupRunner {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(
            UserDetailsRepository userDetailsRepository
    ){
        return args -> {
            if(userDetailsRepository.findByUserRole(UserRole.ADMIN).isEmpty()){
                User admin = User.builder()
                        .username("Admin")
                        .password(bCryptPasswordEncoder.encode("VeryGoodPassword"))
                        .role(UserRole.ADMIN)
                        .build();
                userDetailsRepository.save(admin);
            }
        };
    }



//    @Override
//    public void run(String...args) throws Exception {
//        User admin = new User("Admin", "VeryGoodPassword", UserRole.ADMIN);
//        userDetailsRepository.save(admin);
//    }
}
