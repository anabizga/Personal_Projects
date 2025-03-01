package hotelanimale.is.config;

import hotelanimale.is.repository.ContactRepository;
import hotelanimale.is.model.Contact;
import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ContactConfig {
    @Bean
    CommandLineRunner contactCommandLineRunner(ContactRepository contactRepository, UserRepository userRepository) {
        return args -> {
            User user1 = userRepository.findById(1).orElseThrow();
            User user2 = userRepository.findById(2).orElseThrow();

            Contact contact1 = new Contact("Email", "john@example.com", user1);
            Contact contact2 = new Contact("Phone", "0746716790", user1);
            Contact contact3 = new Contact("Email", "jane@example.com", user2);
            Contact contact4 = new Contact("Phone", "0746716790", user2);

            contactRepository.saveAll(List.of(contact1, contact2, contact3, contact4));
        };
    }
}
