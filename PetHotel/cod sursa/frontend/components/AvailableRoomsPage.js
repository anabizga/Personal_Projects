import React, { useState, useEffect } from 'react';
import '../styles/AvailableRoomsPage.css';

function AvailableRoomsPage() {
    const [locations, setLocations] = useState([]);
    const [selectedLocation, setSelectedLocation] = useState('');
    const [checkIn, setCheckIn] = useState('');
    const [checkOut, setCheckOut] = useState('');
    const [availableRooms, setAvailableRooms] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/locations')
            .then((response) => response.json())
            .then(setLocations)
            .catch((error) => console.error('Error fetching locations:', error));
    }, []);

    const handleSearch = (e) => {
        e.preventDefault();

        if (!selectedLocation || !checkIn || !checkOut) {
            setErrorMessage('Toate câmpurile sunt obligatorii!');
            return;
        }

        setErrorMessage('');
        fetch(
            `http://localhost:8080/api/rooms/available?locationId=${selectedLocation}&checkIn=${checkIn}&checkOut=${checkOut}`
        )
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Eroare la căutarea camerelor disponibile.');
                }
                return response.json();
            })
            .then(setAvailableRooms)
            .catch((error) => {
                console.error('Error fetching available rooms:', error);
                setErrorMessage('Eroare la căutarea camerelor disponibile.');
            });
    };

    return (
        <div className="available-rooms-page">
            <h1>Camere Disponibile</h1>
            <form onSubmit={handleSearch} className="search-form">
                <div className="form-group">
                    <label htmlFor="location">Locație:</label>
                    <select
                        id="location"
                        value={selectedLocation}
                        onChange={(e) => setSelectedLocation(e.target.value)}
                    >
                        <option value="">Selectează o locație</option>
                        {locations.map((location) => (
                            <option key={location.id} value={location.id}>
                                {location.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="checkIn">Check-in:</label>
                    <input
                        type="date"
                        id="checkIn"
                        value={checkIn}
                        onChange={(e) => setCheckIn(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="checkOut">Check-out:</label>
                    <input
                        type="date"
                        id="checkOut"
                        value={checkOut}
                        onChange={(e) => setCheckOut(e.target.value)}
                    />
                </div>
                <button type="submit" className="search-button">
                    Caută
                </button>
            </form>

            {errorMessage && <p className="error-message">{errorMessage}</p>}

            <div className="rooms-list">
                {availableRooms.length === 0 ? (
                    <p>Nu există camere disponibile pentru perioada selectată.</p>
                ) : (
                    availableRooms.map((room) => (
                        <div key={room.id} className="room-card">
                            <h2>{room.roomType}</h2>
                            <p>
                                <strong>Preț:</strong> {room.price} RON/noapte
                            </p>
                            <p>
                                <strong>Locație:</strong> {room.location.name}
                            </p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default AvailableRoomsPage;
