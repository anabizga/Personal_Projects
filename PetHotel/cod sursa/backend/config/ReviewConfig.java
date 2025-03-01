package hotelanimale.is.config;

import hotelanimale.is.model.Client;
import hotelanimale.is.model.Review;
import hotelanimale.is.repository.ClientRepository;
import hotelanimale.is.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ReviewConfig {

    @Bean
    CommandLineRunner reviewCommandLineRunner(ReviewRepository reviewRepository, ClientRepository clientRepository) {
        return args -> {
            Client client1 = clientRepository.findById(1).orElseThrow();
            Client client2 = clientRepository.findById(2).orElseThrow();

            Review review1 = new Review("Great service and friendly staff!", 4.5f, client1);
            Review review2 = new Review("Had a wonderful experience!", 5.0f, client2);

            reviewRepository.saveAll(List.of(review1, review2));
        };
    }
}
