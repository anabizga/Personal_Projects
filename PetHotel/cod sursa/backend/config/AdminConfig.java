package hotelanimale.is.config;

import hotelanimale.is.repository.AdminRepository;
import hotelanimale.is.model.Location;
import hotelanimale.is.repository.LocationRepository;
import hotelanimale.is.model.Admin;
import hotelanimale.is.model.User;
import hotelanimale.is.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AdminConfig {
    @Bean
    CommandLineRunner adminCommandLineRunner(AdminRepository adminRepository, UserRepository userRepository, LocationRepository locationRepository) {
        return args -> {
            User user1 = userRepository.findById(1).orElseThrow();
            User user2 = userRepository.findById(4).orElseThrow();
            Location location = locationRepository.findById(1).orElseThrow();

            Admin admin1 = new Admin("Ana", "Bizga", location, user1);
            Admin admin2 =  new Admin("Clau", "Claudiu", location, user2);
            adminRepository.saveAll(List.of(admin1, admin2));
        };
    }
}
