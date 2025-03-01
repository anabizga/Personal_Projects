package hotelanimale.is.controller;
import hotelanimale.is.model.Review;
import hotelanimale.is.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController( ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping
    public void addReview(@RequestBody Review review) {
        reviewService.addReview(review);
    }

    @DeleteMapping(path = "{reviewId}")
    public void deleteReview(@PathVariable("reviewId") int reviewId) {
        reviewService.deleteReviewById(reviewId);
    }

    @PutMapping(path = "{reviewId}")
    public void updateReview(@PathVariable("reviewId") int reviewId, @RequestParam(required = false) String text, @RequestParam(required = false) float rating) {
        reviewService.updateReview(reviewId, text, rating);
    }

    @GetMapping(path = "/byUserId")
    public List<Review> getReviewsByClientId(@RequestParam int clientId) {
        return reviewService.getReviewsByClientId(clientId);
    }

    @DeleteMapping(path = "/deleteByClientID")
    public void deleteReviewsByClientId(@RequestParam int clientId) {
        reviewService.deleteReviewsByClientId(clientId);
    }
}
