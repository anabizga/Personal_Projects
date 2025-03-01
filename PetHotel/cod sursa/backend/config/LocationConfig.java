package hotelanimale.is.config;

import hotelanimale.is.repository.LocationRepository;
import hotelanimale.is.model.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class LocationConfig {

    @Bean
    @Order(1)
    CommandLineRunner locationCommandLineRunner(LocationRepository locationRepository) {
        return args -> {
            Location loc1 = new Location("Targu-Jiu", "Strada 1 Dec 1918", "0745454545");
            Location loc2 = new Location("Cluj", "Strada Constantin Noica", "0745454545");
            Location loc3 = new Location("Bucuresti", "Calea Victoriei 100", "0721234567");
            Location loc4 = new Location("Timisoara", "Bulevardul Regele Ferdinand 8", "0756123456");
            Location loc5 = new Location("Iasi", "Strada Cuza Voda 5", "0767456789");
            Location loc6 = new Location("Sibiu", "Piata Mare 1", "0778123456");
            Location loc7 = new Location("Brasov", "Strada Republicii 10", "0743123456");
            Location loc8 = new Location("Constanta", "Bulevardul Mamaia 50", "0736123456");
            Location loc9 = new Location("Oradea", "Strada Independentei 12", "0729123456");
            Location loc10 = new Location("Ploiesti", "Strada Mihai Bravu 25", "0745123456");

            locationRepository.saveAll(List.of(loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9, loc10));
        };
    }

}
