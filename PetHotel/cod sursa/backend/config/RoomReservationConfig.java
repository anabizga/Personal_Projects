package hotelanimale.is.config;

import hotelanimale.is.model.Reservation;
import hotelanimale.is.model.RoomReservation;
import hotelanimale.is.repository.ReservationRepository;
import hotelanimale.is.model.Room;
import hotelanimale.is.repository.RoomRepository;
import hotelanimale.is.repository.RoomReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomReservationConfig {
    @Bean
    CommandLineRunner roomReservationRunner(RoomReservationRepository roomReservationRepository,
                                            RoomRepository roomRepository,
                                            ReservationRepository reservationRepository) {
        return args -> {
            Room room = roomRepository.findById(1).orElseThrow();
            Reservation reservation = reservationRepository.findById(1).orElseThrow();

            RoomReservation roomReservation = new RoomReservation(room, reservation);
            roomReservationRepository.save(roomReservation);
        };
    }

}
