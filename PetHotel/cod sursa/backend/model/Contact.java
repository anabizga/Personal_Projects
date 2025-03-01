package hotelanimale.is.model;
import jakarta.persistence.*;

@Entity
@Table
public class Contact {
    @Id
    @SequenceGenerator(
            name = "contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contact_sequence"
    )
    private int id;
    private String type;
    private String info;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public Contact() {
    }

    public Contact(int id, String type, String info, User user) {
        if(type.equals("email")){
            EmailValidator.validate(info);
        }
        if (type.equals("phone")) {
            PhoneNumberValidator.validate(info);
        }
        this.id = id;
        this.type = type;
        this.info = info;
        this.user = user;
    }

    public Contact(String type, String info, User user) {
        if (type.equals("Email")) {
            EmailValidator.validate(info);
        }
        if (type.equals("Phone")) {
            PhoneNumberValidator.validate(info);
        }
        this.type = type;
        this.info = info;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", user=" + user +
                '}';
    }
}
