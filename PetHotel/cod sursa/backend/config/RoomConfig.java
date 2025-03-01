package hotelanimale.is.config;
import hotelanimale.is.model.Location;
import hotelanimale.is.model.Room;
import hotelanimale.is.repository.LocationRepository;
import hotelanimale.is.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class RoomConfig {
    @Order(5)
    @Bean
    CommandLineRunner roomCommandLineRunner(RoomRepository roomRepository, LocationRepository locationRepository) {
        return args -> {
            Location locatie1 = locationRepository.findById(1).orElseThrow();
            Location locatie2 = locationRepository.findById(2).orElseThrow();

            Room Room1 = new Room("Standard", 100.0f, locatie1);
            Room Room2 = new Room("Budget", 150.0f, locatie1);
            Room Room3 = new Room("Standard", 300.0f, locatie1);
            Room Room4 = new Room("Standard", 120.0f, locatie2);
            Room Room5 = new Room("Budget", 180.0f, locatie2);
            Room Room6 = new Room("Luxury", 350.0f, locatie2);
            Room Room7 = new Room("Standard", 110.0f, locatie1);
            Room Room8 = new Room("Budget", 200.0f, locatie2);
            Room Room9 = new Room("Luxury", 500.0f, locatie1);
            Room Room10 = new Room("Budget", 80.0f, locatie2);

            roomRepository.saveAll(List.of(Room1, Room2, Room3, Room4, Room5, Room6, Room7, Room8, Room9, Room10));
        };
    }

}
