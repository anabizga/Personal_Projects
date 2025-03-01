package hotelanimale.is.service;
import hotelanimale.is.model.Client;
import hotelanimale.is.model.Review;
import hotelanimale.is.repository.ClientRepository;
import hotelanimale.is.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ClientRepository clientRepository) {
        this.reviewRepository = reviewRepository;
        this.clientRepository = clientRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new IllegalArgumentException("This review does not exist");
        }
    }

    public void addReview(Review review) {
        Client client = clientRepository.findById(review.getClient().getId()).orElseThrow(()-> new IllegalArgumentException("Client with id " + review.getClient().getId() + " does not exist"));
        review.setClient(client);
        reviewRepository.save(review);
    }

    public void deleteReviewById(int id) {
        boolean exists = reviewRepository.existsById(id);
        if (exists) {
            reviewRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("This review does not exist");
        }
    }

    @Transactional
    public void updateReview(int reviewId, String text, float rating) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new IllegalArgumentException("This review does not exist");
        }
        Review reviewToUpdate = review.get();
        if(text!=null && !text.isEmpty() && !reviewToUpdate.getText().equals(text)) {
            reviewToUpdate.setText(text);
        }
        if(rating!=0 && reviewToUpdate.getRating() != rating) {
            reviewToUpdate.setRating(rating);
        }
    }

    public List<Review> getReviewsByClientId(int clientId) {
        Optional<List<Review>> reviews = reviewRepository.findByClientId(clientId);
        if (reviews.isPresent()) {
            return reviews.get();
        }
        throw new IllegalArgumentException("There is no reviews with the client id " + clientId);
    }

    public void deleteReviewsByClientId(int clientId) {
        reviewRepository.deleteByClientId(clientId);
    }
}
