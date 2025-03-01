package hotelanimale.is.model;

import jakarta.persistence.*;

@Entity
@Table
public class RoomReservation {
    @Id
    @SequenceGenerator(
            name = "room_reservation_sequence",
            sequenceName = "room_reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_reservation_sequence"
    )
    private int id;

    @ManyToOne
    @JoinColumn(name = "idRoom", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "idReservation", nullable = false)
    private Reservation reservation;

    public RoomReservation() {}

    public RoomReservation(Room room, Reservation reservation) {
        this.room = room;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "RoomReservation{" +
                "id=" + id +
                ", room=" + room +
                ", reservation=" + reservation +
                '}';
    }
}
