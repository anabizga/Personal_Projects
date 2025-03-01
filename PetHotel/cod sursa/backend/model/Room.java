package hotelanimale.is.model;

import jakarta.persistence.*;

@Entity
@Table
public class Room {
    @Id
    @SequenceGenerator(
            name = "Room_sequence",
            sequenceName = "Room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Room_sequence"
    )
    private int id;
    private String roomType;
    private float price;
    @ManyToOne
    @JoinColumn(name = "idLocation", nullable = false)
    private Location location;

    public Room() {
    }

    public Room(int id, String roomType, float price, Location location) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.location = location;
    }

    public Room(String roomType, float price, Location location) {
        this.price = price;
        this.roomType = roomType;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", price='" + price + '\'' +
                ", location='" + location.getName() + '\'' +
                '}';
    }
}
