package hotelanimale.is.config;

import hotelanimale.is.model.Client;
import hotelanimale.is.model.Reservation;
import hotelanimale.is.repository.ClientRepository;
import hotelanimale.is.model.Location;
import hotelanimale.is.repository.LocationRepository;
import hotelanimale.is.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ReservationConfig {

    @Order(6)
    @Bean
    CommandLineRunner reservationCommandLineRunner(ReservationRepository reservationRepository,
                                                   ClientRepository clientRepository,
                                                   LocationRepository locationRepository) {
        return args -> {

            Client client1 = clientRepository.findById(1).orElseThrow();
            Client client2 = clientRepository.findById(2).orElseThrow();

            Location location1 = locationRepository.findById(1).orElseThrow();
            Location location2 = locationRepository.findById(2).orElseThrow();

            Reservation reservation1 = new Reservation(
                    LocalDate.parse("2025-01-14"),
                    LocalDate.parse("2025-01-16"),
                    "În viitor",
                    client1,
                    location1
            );
            Reservation reservation2 = new Reservation(
                    LocalDate.parse("2025-01-12"),
                    LocalDate.parse("2025-01-13"),
                    "Finalizată",
                    client1,
                    location1
            );

            Reservation reservation3 = new Reservation(
                    LocalDate.parse("2025-01-15"),
                    LocalDate.parse("2024-01-16"),
                    "În viitor",
                    client1,
                    location2
            );

            reservationRepository.saveAll(List.of(reservation1, reservation2, reservation3));
        };
    }
}
