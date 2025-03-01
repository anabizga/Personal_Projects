package hotelanimale.is.service;
import hotelanimale.is.model.*;
import hotelanimale.is.repository.*;
import hotelanimale.is.model.Room;
import hotelanimale.is.repository.RoomRepository;
import hotelanimale.is.model.RoomReservation;
import hotelanimale.is.repository.RoomReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final AnimalRepository animalRepository;
    private final AnimalReservationRepository animalReservationRepository;
    private final RoomReservationRepository roomReservationRepository;
    private final ClientRepository clientRepository;
    private final LocationRepository locationRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, AnimalRepository animalRepository, AnimalReservationRepository animalReservationRepository, RoomReservationRepository roomReservationRepository, ClientRepository clientRepository, LocationRepository locationRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.animalRepository = animalRepository;
        this.animalReservationRepository = animalReservationRepository;
        this.roomReservationRepository = roomReservationRepository;
        this.clientRepository = clientRepository;
        this.locationRepository = locationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(int id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        boolean exists = reservationRepository.existsById(id);
        if (exists) {
            animalReservationRepository.deleteByReservation_Id(id);
            roomReservationRepository.deleteByReservation_Id(id);
            reservationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }
    }

    @Transactional
    public void updateReservation(int reservationId, LocalDate checkIn, LocalDate checkOut, String status) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if(reservation.isEmpty()){
            throw new IllegalArgumentException("Reservation with id " + reservationId + " not found");
        }
        Reservation reservationToUpdate = reservation.get();
        if(checkIn!=null && !checkIn.equals(reservationToUpdate.getCheckIn())){
            reservationToUpdate.setCheckIn(checkIn);
        }
        if(checkOut!=null && !checkOut.equals(reservationToUpdate.getCheckOut())){
            reservationToUpdate.setCheckOut(checkOut);
        }
        if(status!=null && !status.isEmpty() && !status.equals(reservationToUpdate.getState())){
            reservationToUpdate.setState(status);
        }
    }

    public List<Reservation> getReservationsByClientId(int clientId) {
        Optional<List<Reservation>> reservations = reservationRepository.findReservationByClientId(clientId);
        if(reservations.isPresent()){
            return reservations.get();
        } else {
            throw new IllegalArgumentException("There are no reservations for client " + clientId + " not found");
        }
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        Optional<List<Reservation>> reservations = reservationRepository.findReservationByUserId(userId);
        if(reservations.isPresent()){
            return reservations.get();
        } else {
            throw new IllegalArgumentException("There are no reservations for user " + userId + " not found");
        }
    }

    public List<Reservation> getReservationsByLocationId(int locationId) {
        Optional<List<Reservation>> reservations = reservationRepository.findReservationByLocationId(locationId);
        if(reservations.isPresent()){
            return reservations.get();
        } else {
            throw new IllegalArgumentException("There are no reservations for location " + locationId + " not found");
        }
    }

    public void deleteReservationByClientId(int clientId) {
        reservationRepository.deleteReservationByClientId(clientId);
    }

    public void deleteReservationByLocationId(int locationId) {
        reservationRepository.deleteReservationByLocationId(locationId);
    }

    public void checkIn(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervarea nu a fost găsită!"));

        LocalDate today = LocalDate.now();
        if (!reservation.getCheckIn().equals(today)) {
            throw new IllegalArgumentException("Rezervarea nu este eligibilă pentru Check-in.");
        }
        if (!"În viitor".equals(reservation.getState())) {
            throw new IllegalArgumentException("Rezervarea trebuie să aibă starea 'În viitor' pentru Check-in.");
        }

        reservation.setState("În desfășurare");
        reservationRepository.save(reservation);
    }

    public void checkOut(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Rezervarea nu a fost găsită!"));

        LocalDate today = LocalDate.now();

        if (!reservation.getCheckOut().equals(today)) {
            throw new IllegalArgumentException("Rezervarea nu este eligibilă pentru Check-out.");
        }
        if (!"În desfășurare".equals(reservation.getState())) {
            throw new IllegalArgumentException("Rezervarea trebuie să aibă starea 'În desfășurare' pentru Check-out.");
        }

        reservation.setState("Finalizată");
        reservationRepository.save(reservation);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cancelExpiredReservations() {
        LocalDate today = LocalDate.now();

        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations("În viitor", today);

        for (Reservation reservation : expiredReservations) {
            reservation.setState("Anulată");
            reservationRepository.save(reservation);
        }
    }

    public Reservation createReservation(ReservationRequest request) {
        if (request.getCheckIn().isAfter(request.getCheckOut())) {
            throw new IllegalArgumentException("Data de check-in trebuie să fie înainte de check-out.");
        }

        Optional<Location> locationSearched = locationRepository.findById(request.getLocationId());
        Optional<Client> clientSearched = clientRepository.findById(request.getClientId());
        Location location = locationSearched.get();
        Client client = clientSearched.get();

        Reservation reservation = new Reservation(
                request.getCheckIn(),
                request.getCheckOut(),
                "În viitor",
                client,
                location
        );
        reservation = reservationRepository.save(reservation);

        for (int animalId : request.getAnimalIds()) {
            Optional<Animal> animalSearched = animalRepository.findById(animalId);
            Animal animal = animalSearched.get();
            if (!animal.getClient().equals(client)) {
                throw new IllegalArgumentException("Un animal nu aparține clientului.");
            }
            AnimalReservation animalReservation = new AnimalReservation(animal, reservation);
            animalReservationRepository.save(animalReservation);
        }

        for (int roomId : request.getRoomIds()) {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new IllegalArgumentException("Camera nu există."));
            RoomReservation roomReservation = new RoomReservation(room, reservation);
            roomReservationRepository.save(roomReservation);
        }

        return reservation;
    }

}
