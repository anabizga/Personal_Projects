package hotelanimale.is.config;

import hotelanimale.is.repository.ClientRepository;
import hotelanimale.is.model.Client;
import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class ClientConfig {
    @Bean
    @Order(3)
    CommandLineRunner clientCommandLineRunner(ClientRepository clientRepository, UserRepository userRepository) {
        return args -> {
            User user1 = userRepository.findById(2).orElseThrow();
            User user2 = userRepository.findById(3).orElseThrow();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Client client1 = new Client("Alice", "Brown", LocalDate.parse("1990-05-12"), user1);
            Client client2 = new Client("Bob", "White", LocalDate.parse("1985-11-20"), user2);
            clientRepository.saveAll(List.of(client1, client2));
        };
    }
}
