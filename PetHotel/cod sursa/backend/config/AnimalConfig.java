package hotelanimale.is.config;

import hotelanimale.is.repository.AnimalRepository;
import hotelanimale.is.model.Animal;
import hotelanimale.is.model.Client;
import hotelanimale.is.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class AnimalConfig {
    @Bean
    @Order(4)
    CommandLineRunner animalCommandLineRunner(AnimalRepository animalRepository, ClientRepository clientRepository) {
        return args -> {
            Client client1 = clientRepository.findById(1).orElseThrow();
            Client client2 = clientRepository.findById(2).orElseThrow();

            Animal animal1 = new Animal("Bella", 3, "Female", client1);
            Animal animal2 = new Animal("Max", 5, "Male", client1);
            Animal animal3 = new Animal("Charlie", 2, "Male", client2);
            Animal animal4 = new Animal("Lucy", 4, "Female", client2);
            Animal animal5 = new Animal("Daisy", 1, "Female", client1);
            Animal animal6 = new Animal("Milo", 6, "Male", client2);
            Animal animal7 = new Animal("Coco", 3, "Female", client1);
            Animal animal8 = new Animal("Rocky", 7, "Male", client2);
            Animal animal9 = new Animal("Luna", 2, "Female", client1);
            Animal animal10 = new Animal("Buddy", 8, "Male", client2);

            animalRepository.saveAll(List.of(animal1, animal2, animal3, animal4, animal5, animal6, animal7, animal8, animal9, animal10));
        };
    }
}
