import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import '../styles/NewReviewPage.css'

function NewReviewPage({ user }) {
    const [searchParams] = useSearchParams();
    const reservationId = searchParams.get('reservationId');
    const [text, setText] = useState('');
    const [rating, setRating] = useState('');
    const [clientId, setClientId] = useState(null);
    const [clientDetails, setClientDetails] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }

        fetch(`http://localhost:8080/api/clients/byUserId?userId=${user.id}`)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Failed to fetch clientId');
            })
            .then((data) => {
                setClientId(data.id);
                setClientDetails(data);
            })
            .catch((error) => {
                console.error('Error fetching clientId:', error);
            });
    }, [user, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!clientId) {
            console.error('Client ID is not available');
            return;
        }

        fetch('http://localhost:8080/api/reviews', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                text,
                rating: parseFloat(rating),
                client: { id: clientId },
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Server error: ${response.status} ${response.statusText}`);
                }
                return response.text();
            })
            .then((data) => {
                try {
                    const jsonData = JSON.parse(data);
                    console.log('Review submitted:', jsonData);
                    setSuccessMessage('Recenzia a fost trimisă cu succes!');
                    setTimeout(() => navigate('/history'), 2000);
                } catch (error) {
                    console.warn('Received non-JSON response:', data);
                    setSuccessMessage('Recenzia a fost trimisă cu succes!');
                    setTimeout(() => navigate('/history'), 2000);
                }
            })
            .catch((error) => {
                console.error('Error submitting review:', error);
            });
    };

    return (
        <div className="new-review-page">
            <h1>Lasă o recenzie</h1>
            {successMessage && (
                <div className="success-message">
                    <p>{successMessage}</p>
                </div>
            )}
            <div className="review-card">
                <h2>Detalii Client</h2>
                {clientId && (
                    <p>
                        <strong>Nume:</strong> {clientDetails.firstName} {clientDetails.lastName}
                    </p>
                )}
                {clientId && (
                    <p>
                        <strong>Data nașterii:</strong> {clientDetails.dateOfBirth}
                    </p>
                )}
                <form onSubmit={handleSubmit}>
                    <label htmlFor="rating">Rating (1-5):</label>
                    <input
                        id="rating"
                        type="number"
                        min="1"
                        max="5"
                        step="0.1"
                        value={rating}
                        onChange={(e) => setRating(e.target.value)}
                        required
                    />
                    <label htmlFor="text">Comentariu:</label>
                    <textarea
                        id="text"
                        value={text}
                        onChange={(e) => setText(e.target.value)}
                        required
                    />
                    <button type="submit" disabled={!clientId}>
                        Trimite Recenzia
                    </button>
                </form>
            </div>
        </div>
    );
}

export default NewReviewPage;