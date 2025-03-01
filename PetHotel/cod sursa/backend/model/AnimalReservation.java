package hotelanimale.is.model;

import jakarta.persistence.*;

@Entity
@Table
public class AnimalReservation {
    @Id
    @SequenceGenerator(
            name = "animal_reservation_sequence",
            sequenceName = "animal_reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "animal_reservation_sequence"
    )
    private int id;

    @ManyToOne
    @JoinColumn(name = "idAnimal", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "idReservation", nullable = false)
    private Reservation reservation;

    public AnimalReservation() {}

    public AnimalReservation(Animal animal, Reservation reservation) {
        this.animal = animal;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "AnimalReservation{" +
                "id=" + id +
                ", animal=" + animal +
                ", reservation=" + reservation +
                '}';
    }
}
