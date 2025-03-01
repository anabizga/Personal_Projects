package hotelanimale.is.model;

import java.time.LocalDate;
import java.util.*;

public class ReservationRequest {
    private int clientId;
    private int locationId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> animalIds;
    private List<Integer> roomIds;

    public ReservationRequest(int clientId, int locationId, LocalDate checkIn, LocalDate checkOut, List<Integer> animalIds, List<Integer> roomIds) {
        this.clientId = clientId;
        this.locationId = locationId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.animalIds = animalIds;
        this.roomIds = roomIds;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getAnimalIds() {
        return animalIds;
    }

    public void setAnimalIds(List<Integer> animalIds) {
        this.animalIds = animalIds;
    }

    public List<Integer> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Integer> roomIds) {
        this.roomIds = roomIds;
    }
}
