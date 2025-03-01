package hotelanimale.is.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
public class Review {
    @Id
    @SequenceGenerator(
            name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_sequence"
    )
    private int id;
    private String text;
    private float rating;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date datePosted;
    @ManyToOne
    @JoinColumn(name = "idClient", nullable = false)
    private Client client;

    public Review(){
    }

    public Review(String text, float rating, Client client) {
        this.text = text;
        this.rating = rating;
        this.datePosted = new Date();
        this.client = client;
    }

    public Review(int id, String text, float rating, Client client) {
        this.text = text;
        this.rating = rating;
        this.datePosted = new Date();
        this.client = client;
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        if (this.datePosted == null) {
            this.datePosted = new Date();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(datePosted);
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", datePosted=" + formattedDate +
                ", client=" + client +
                '}';
    }
}
