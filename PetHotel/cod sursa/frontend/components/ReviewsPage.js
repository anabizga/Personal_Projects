import React, {useState, useEffect} from 'react';
import '../styles/ReviewsPage.css';

function ReviewsPage() {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/reviews')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setReviews(data);
                console.log(data);
            })
            .catch((error) => console.error('Error fetching reviews:', error));
    }, []);


    return (
        <div className="reviews-page">
            <h1>Recenzii</h1>
            <div className="reviews-list">
                {reviews.length > 0 ? (
                    reviews.map((review) => (
                        <div key={review.id} className="review-card">
                            <h2>{review.client.firstName + " " + review.client.lastName}</h2>
                            <p>{review.text}</p>
                            <div className="review-footer">
                                <span className="rating">Rating: {review.rating}/5</span>
                                <span className="date">Postat pe: {review.datePosted}</span>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>Nu există recenzii disponibile.</p>
                )}
            </div>
        </div>
    );
}

export default ReviewsPage;
