package hotelanimale.is.model;
import jakarta.persistence.*;

@Entity
@Table
public class Animal {
    @Id
    @SequenceGenerator(
            name = "animal_sequence",
            sequenceName = "animal_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "animal_sequence"
    )
    private int id;
    private String name;
    private int age;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "idClient", nullable = false)
    private Client client;

    public Animal() {}

    public Animal(String name, int age, String gender, Client client) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.client = client;
    }

    public Animal(int id, String name, int age, String gender, Client client) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", client=" + client +
                '}';
    }
}
