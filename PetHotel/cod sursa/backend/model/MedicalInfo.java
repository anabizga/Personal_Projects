package hotelanimale.is.model;
import jakarta.persistence.*;

@Entity
@Table
public class MedicalInfo {
    @Id
    @SequenceGenerator(
            name = "medical_sequence",
            sequenceName = "medical_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "medical_sequence"
    )
    private int id;
    private String medicalDescription;
    private String medicalType;
    @ManyToOne
    @JoinColumn(name = "idAnimal", nullable = false)
    private Animal animal;

    public MedicalInfo() {
    }

    public MedicalInfo(String medicalDescription, String medicalType, Animal animal) {
        this.medicalDescription = medicalDescription;
        this.medicalType = medicalType;
        this.animal = animal;
    }

    public MedicalInfo(int id, String medicalDescription, String medicalType, Animal animal) {
        this.medicalDescription = medicalDescription;
        this.medicalType = medicalType;
        this.animal = animal;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicalDescription() {
        return medicalDescription;
    }

    public void setMedicalDescription(String medicalDescription) {
        this.medicalDescription = medicalDescription;
    }

    public String getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(String medicalType) {
        this.medicalType = medicalType;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "MedicalInfo{" +
                "id=" + id +
                ", medicalDescription='" + medicalDescription + '\'' +
                ", medicalType='" + medicalType + '\'' +
                ", animal=" + animal +
                '}';
    }
}