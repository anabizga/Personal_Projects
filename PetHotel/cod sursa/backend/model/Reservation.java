package hotelanimale.is.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkOut;
    private String state;
    @ManyToOne
    @JoinColumn(name = "idClient", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "idLocation", nullable = false)
    private Location location;

    public Reservation() {}

    public Reservation(LocalDate checkIn, LocalDate checkOut, String state, Client client, Location location) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.state = state;
        this.client = client;
        this.location = location;
    }

    public Reservation(int id, LocalDate checkIn, LocalDate checkOut, String state, Client client, Location location) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.state = state;
        this.client = client;
        this.location = location;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", state='" + state + '\'' +
                ", client=" + client +
                ", location=" + location +
                '}';
    }
}
