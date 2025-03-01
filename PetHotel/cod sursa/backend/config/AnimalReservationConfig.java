package hotelanimale.is.config;

import hotelanimale.is.repository.AnimalReservationRepository;
import hotelanimale.is.model.Animal;
import hotelanimale.is.model.AnimalReservation;
import hotelanimale.is.repository.AnimalRepository;
import hotelanimale.is.model.Reservation;
import hotelanimale.is.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalReservationConfig {
    @Bean
    CommandLineRunner animalReservationRunner(AnimalReservationRepository animalReservationRepository,
                                              AnimalRepository animalRepository,
                                              ReservationRepository reservationRepository) {
        return args -> {
            Animal animal = animalRepository.findById(1).orElseThrow();
            Reservation reservation = reservationRepository.findById(1).orElseThrow();

            AnimalReservation animalReservation = new AnimalReservation(animal, reservation);
            animalReservationRepository.save(animalReservation);
        };
    }

}
