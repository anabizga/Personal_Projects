package hotelanimale.is.service;

import hotelanimale.is.model.Animal;
import hotelanimale.is.model.AnimalReservation;
import hotelanimale.is.repository.AnimalReservationRepository;
import hotelanimale.is.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalReservationService {
    private final AnimalReservationRepository animalReservationRepository;
    private final AnimalService animalService;
    private final ReservationService reservationService;

    @Autowired
    public AnimalReservationService(AnimalReservationRepository animalReservationRepository,
                                    AnimalService animalService,
                                    ReservationService reservationService) {
        this.animalReservationRepository = animalReservationRepository;
        this.animalService = animalService;
        this.reservationService = reservationService;
    }

    public void addAnimalToReservation(int animalId, int reservationId) {
        Animal animal = animalService.findAnimalById(animalId);
        Reservation reservation = reservationService.getReservationById(reservationId);

        if (animal == null || reservation == null) {
            throw new IllegalArgumentException("Animal or Reservation not found");
        }

        AnimalReservation animalReservation = new AnimalReservation(animal, reservation);
        animalReservationRepository.save(animalReservation);
    }

    public List<AnimalReservation> getAllAnimalReservations() {
        return animalReservationRepository.findAll();
    }

    public void deleteByReservationId(int reservationId) {
        animalReservationRepository.deleteByReservation_Id(reservationId);
    }

    public void deleteByAnimalId(int animalId) {
        animalReservationRepository.deleteByAnimal_Id(animalId);
    }

    public List<Animal> getAnimalsByReservationId(int reservationId) {
        return animalReservationRepository.findAnimalsByReservationId(reservationId);
    }
}
