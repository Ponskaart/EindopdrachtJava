package nl.bd.eindopdrachtjava;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Class runs before actual program starts and loads database with an admin user and a customer user.
 */
@Configuration
@AllArgsConstructor
public class CommandLineAppStartupRunner {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUserRole(UserRole.ADMIN).isEmpty()){
                User admin = User.builder()
                        .username("Admin")
                        .password(bCryptPasswordEncoder.encode("VeryGoodPassword"))
                        .role(UserRole.ADMIN)
                        .build();
                userRepository.save(admin);
            }

            if(userRepository.findByUserRole(UserRole.CUSTOMER).isEmpty()){
                User customer = User.builder()
                        .username("Customer")
                        .password(bCryptPasswordEncoder.encode("EvenBetterPassword"))
                        .role(UserRole.CUSTOMER)
                        .build();
                userRepository.save(customer);
            }

            if(userRepository.findByUserRole(UserRole.EMPLOYEE).isEmpty()){
                User employee = User.builder()
                        .username("Employee")
                        .password(bCryptPasswordEncoder.encode("TheBestPassword"))
                        .role(UserRole.EMPLOYEE)
                        .build();
                userRepository.save(employee);
            }
        };
    }
}
