package nl.bd.eindopdrachtjava;

import lombok.AllArgsConstructor;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.entities.CoverArt;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.models.entities.User;
import nl.bd.eindopdrachtjava.models.enums.UserRole;
import nl.bd.eindopdrachtjava.repositories.ArtistRepository;
import nl.bd.eindopdrachtjava.repositories.CoverArtRepository;
import nl.bd.eindopdrachtjava.repositories.RecordRepository;
import nl.bd.eindopdrachtjava.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Class runs before actual program starts and loads database with an admin user and a customer user.
 */
@Profile("!test")
@Configuration
@AllArgsConstructor
public class CommandLineAppStartupRunner {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,
                                        ArtistRepository artistRepository,
                                        RecordRepository recordRepository,
                                        CoverArtRepository coverArtRepository) {
        return args -> {
            if (userRepository.findByUserRole(UserRole.ADMIN).isEmpty()) {
                User admin = User.builder()
                        .username("Admin")
                        .password(bCryptPasswordEncoder.encode("VeryGoodPassword"))
                        .role(UserRole.ADMIN)
                        .build();
                userRepository.save(admin);
            }

            if (userRepository.findByUserRole(UserRole.CUSTOMER).isEmpty()) {
                User customer = User.builder()
                        .username("Customer")
                        .password(bCryptPasswordEncoder.encode("EvenBetterPassword"))
                        .role(UserRole.CUSTOMER)
                        .build();
                userRepository.save(customer);
            }

            if (userRepository.findByUserRole(UserRole.EMPLOYEE).isEmpty()) {
                User employee = User.builder()
                        .username("Employee")
                        .password(bCryptPasswordEncoder.encode("TheBestPassword"))
                        .role(UserRole.EMPLOYEE)
                        .build();
                userRepository.save(employee);
            }

            if (artistRepository.findById(1L).isEmpty()) {
                Artist artist = Artist.builder()
                        .artistName("Ben de Testartiest")
                        .established(2022)
                        .build();
                artistRepository.save(artist);
            }

            if (recordRepository.findById(1L).isEmpty()) {
                Record record = Record.builder()
                        .artist(artistRepository.findById(1L).get())
                        .title("Hij was maar een testartiest")
                        .genre("Schlager")
                        .label("Test Records")
                        .color("Green")
                        .year(2022)
                        .country("Germany")
                        .price(0.99)
                        .qtyInStock(99)
                        .build();
                recordRepository.save(record);
            }

            if (coverArtRepository.findById(1L).isEmpty()) {
                BufferedImage bufferimage = ImageIO.read(new File("src/main/resources/CoverArtTest.PNG"));
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferimage, "png", output);
                byte[] data = output.toByteArray();

                CoverArt coverArt = new CoverArt();
                coverArt.setRecord(recordRepository.findById(1L).get());
                coverArt.setContent(data);

                coverArtRepository.save(coverArt);
            }

        };
    }
}
