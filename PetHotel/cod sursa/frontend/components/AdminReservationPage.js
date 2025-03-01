import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/AdminReservationPage.css';

function AdminReservationPage({ user }) {
    const [reservations, setReservations] = useState([]);
    const [selectedReservation, setSelectedReservation] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user || user.userType !== 'Admin') {
            navigate('/');
            return;
        }

        fetch(`http://localhost:8080/api/admins/user/${user.id}/reservations`)
            .then((response) => response.json())
            .then(setReservations)
            .catch((error) => console.error('Error fetching reservations:', error));
    }, [user, navigate]);

    const handleViewDetails = (reservationId) => {
        fetch(`http://localhost:8080/api/reservations/detailsForAdmin?reservationId=${reservationId}`)
            .then((response) => response.json())
            .then(setSelectedReservation)
            .catch((error) => console.error('Error fetching reservation details:', error));
    };

    const handleDeleteReservation = (reservationId) => {
        if (window.confirm('Ești sigur că vrei să ștergi această rezervare?')) {
            fetch(`http://localhost:8080/api/reservations/${reservationId}`, {
                method: 'DELETE',
            })
                .then((response) => {
                    if (response.ok) {
                        setReservations((prev) =>
                            prev.filter((reservation) => reservation.id !== reservationId)
                        );
                        alert('Rezervarea a fost ștearsă cu succes.');
                    } else {
                        throw new Error('Eroare la ștergerea rezervării');
                    }
                })
                .catch((error) => {
                    console.error('Error deleting reservation:', error);
                    alert('Eroare la ștergerea rezervării.');
                });
        }
    };

    return (
        <div className="admin-page">
            <h1>Rezervări - Admin</h1>
            <div className="admin-reservations-list">
                {reservations.length === 0 ? (
                    <p>Nu există rezervări pentru această locație.</p>
                ) : (
                    reservations.map((reservation) => (
                        <div key={reservation.id} className="admin-reservation-card">
                            <h2>Rezervare #{reservation.id}</h2>
                            <p><strong>Check-in:</strong> {reservation.checkIn}</p>
                            <p><strong>Check-out:</strong> {reservation.checkOut}</p>
                            <p><strong>Stare:</strong> {reservation.state}</p>

                            {/* Afișare detalii despre client */}
                            {reservation.client ? (
                                <p>
                                    <strong>Client:</strong> {reservation.client.firstName} {reservation.client.lastName}
                                </p>
                            ) : (
                                <p><strong>Client:</strong> Informații indisponibile</p>
                            )}

                            <div className="reservation-actions">
                                <button
                                    className="details-button"
                                    onClick={() => handleViewDetails(reservation.id)}
                                    style={{marginRight: '10px'}}
                                >
                                    Detalii
                                </button>
                                <button
                                    className="delete-button"
                                    onClick={() => handleDeleteReservation(reservation.id)}
                                >
                                    Șterge
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>

            {/* Detalii rezervare */}
            {selectedReservation && (
                <div className="admin-reservation-details">
                    <h2>Detalii Rezervare</h2>
                    <p><strong>Locație:</strong> {selectedReservation.location?.name}</p>
                    <p><strong>Check-in:</strong> {selectedReservation.checkIn}</p>
                    <p><strong>Check-out:</strong> {selectedReservation.checkOut}</p>
                    <p><strong>Stare:</strong> {selectedReservation.state}</p>
                    <p><strong>Client:</strong> {selectedReservation.client.firstName} {selectedReservation.client.lastName}</p>

                    {/* Contactele clientului */}
                    <h3>Contacte Client</h3>
                    {selectedReservation.client?.contacts?.length > 0 ? (
                        <ul>
                            {selectedReservation.client.contacts.map((contact) => (
                                <li key={contact.id}>
                                    <strong>{contact.type}:</strong> {contact.info}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Nu există contacte pentru acest client.</p>
                    )}

                    {/* Lista animalelor și detaliile medicale */}
                    <h3>Animale și informații medicale</h3>
                    {selectedReservation.animals?.length > 0 ? (
                        <ul>
                            {selectedReservation.animals.map((animal) => (
                                <li key={animal.id}>
                                    <p>
                                        <strong>{animal.name}</strong> - {animal.age} ani - {animal.gender}
                                    </p>
                                    {animal.medicalInfos?.length > 0 ? (
                                        <ul>
                                            {animal.medicalInfos.map((info) => (
                                                <li key={info.id}>
                                                    <strong>Tip:</strong> {info.medicalType}, <strong>Descriere:</strong> {info.medicalDescription}
                                                </li>
                                            ))}
                                        </ul>
                                    ) : (
                                        <p>Nu există informații medicale pentru acest animal.</p>
                                    )}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Nu există animale asociate cu această rezervare.</p>
                    )}


                    {/* Lista camerelor rezervate */}
                    <h3>Camere</h3>
                    {selectedReservation.rooms?.length > 0 ? (
                        <ul>
                            {selectedReservation.rooms.map((room) => (
                                <li key={room.id}>
                                    {room.roomType} - {room.price} RON
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Nu există camere asociate cu această rezervare.</p>
                    )}
                </div>
            )}
        </div>
    );
}

export default AdminReservationPage;
