import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/HistoryPage.css';

function HistoryPage({ user }) {
    const [reservations, setReservations] = useState([]);
    const [selectedReservation, setSelectedReservation] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user || user.userType !== 'Client') {
            navigate('/');
            return;
        }

        fetch(`http://localhost:8080/api/reservations/byUserId?userId=${user.id}`)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Failed to fetch reservations');
            })
            .then((data) => {
                setReservations(data);
            })
            .catch((error) => {
                console.error('Error fetching reservations:', error);
            });
    }, [user, navigate]);

    const handleLeaveReview = (reservationId) => {
        if (!reservationId) {
            console.error('Invalid reservation ID');
            return;
        }
        navigate(`/reviews/new?reservationId=${reservationId}`);
    };

    const handleViewDetails = (reservationId) => {
        if (!reservationId) {
            console.error('Invalid reservation ID');
            return;
        }

        fetch(`http://localhost:8080/api/reservations/details?reservationId=${reservationId}`)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Failed to fetch reservation details');
            })
            .then((data) => {
                setSelectedReservation(data);
            })
            .catch((error) => {
                console.error('Error fetching reservation details:', error);
            });
    };

    return (
        <div className="history-page">
            <h1>Istoric Rezervări</h1>
            <div className="reservations-list">
                {reservations.length === 0 ? (
                    <p>Nu există rezervări.</p>
                ) : (
                    reservations.map((reservation) => (
                        <div key={reservation.id} className="reservation-card">
                            <h2>Rezervare {reservation.location.name}</h2>
                            <p><strong>Check-in:</strong> {reservation.checkIn}</p>
                            <p><strong>Check-out:</strong> {reservation.checkOut}</p>
                            <p><strong>Stare:</strong> {reservation.state}</p>
                            <div className="reservation-actions">
                                {reservation.state === 'Finalizată' && (
                                    <button
                                        className="leave-review-button"
                                        onClick={() => handleLeaveReview(reservation.id)}
                                        style={{marginRight: '10px'}}
                                    >
                                        Lasă un review
                                    </button>
                                )}
                                <button
                                    className="details-button"
                                    onClick={() => handleViewDetails(reservation.id)}
                                >
                                    Detalii
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>
            {selectedReservation && (
                <div className="reservation-details">
                    <h2>Detalii Rezervare</h2>
                    <p><strong>Locație:</strong> {selectedReservation.location.name}</p>
                    <p><strong>Check-in:</strong> {selectedReservation.checkIn}</p>
                    <p><strong>Check-out:</strong> {selectedReservation.checkOut}</p>
                    <p><strong>Stare:</strong> {selectedReservation.state}</p>
                    <h3>Animale:</h3>
                    {selectedReservation.animals.length === 0 ? (
                        <p>Nu există animale asociate cu această rezervare.</p>
                    ) : (
                        <ul>
                            {selectedReservation.animals.map((animal) => (
                                <li key={animal.id}>
                                    {animal.name} - {animal.age} ani - {animal.gender}
                                </li>
                            ))}
                        </ul>
                    )}
                    <h3>Camere:</h3>
                    {selectedReservation.rooms.length === 0 ? (
                        <p>Nu există camere asociate cu această rezervare.</p>
                    ) : (
                        <ul>
                            {selectedReservation.rooms.map((room) => (
                                <li key={room.id}>
                                    {room.roomType} - {room.price} RON
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            )}
        </div>
    );
}

export default HistoryPage;
