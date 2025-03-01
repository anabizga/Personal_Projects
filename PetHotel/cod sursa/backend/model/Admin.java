package hotelanimale.is.model;
import jakarta.persistence.*;

@Entity
@Table
public class Admin {
    @Id
    @SequenceGenerator(
            name = "admin_sequence",
            sequenceName = "admin_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin_sequence"
    )
    private int id;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "idLocation", nullable = false)
    private Location location;
    @OneToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public Admin() {}

    public Admin(int id, String firstName, String lastName, Location location, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.user = user;
    }

    public Admin(String firstName, String lastName, Location location, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location=" + location +
                ", user=" + user +
                '}';
    }
}
