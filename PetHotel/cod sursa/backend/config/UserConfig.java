package hotelanimale.is.config;

import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    @Order(2)
    CommandLineRunner userCommandLineRunner(UserRepository userRepository) {
        return args -> {
            User user1 = new User("john_doe", "password123", "Admin");
            User user2 = new User("jane_smith", "mypassword", "Client");
            User user3 = new User("michael_brown", "securepass", "Client");
            User user4 = new User("alice_white", "password", "Admin");
            User user5 = new User("anabizga", "29042004", "SuperAdmin");

            userRepository.saveAll(List.of(user1, user2, user3, user4, user5));
        };
    }

}
