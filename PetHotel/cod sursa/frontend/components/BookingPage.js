import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/BookingPage.css';

function BookingPage({ user }) {
    const [clientDetails, setClientDetails] = useState(null);
    const [animals, setAnimals] = useState([]);
    const [locations, setLocations] = useState([]);
    const [selectedLocation, setSelectedLocation] = useState('');
    const [selectedAnimals, setSelectedAnimals] = useState([]);
    const [checkIn, setCheckIn] = useState('');
    const [checkOut, setCheckOut] = useState('');
    const [availableRooms, setAvailableRooms] = useState([]);
    const [selectedRooms, setSelectedRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');
    const [errorCheckIn, setErrorCheckIn] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }

        const fetchData = async () => {
            try {
                const clientResponse = await fetch(
                    `http://localhost:8080/api/clients/byUserId?userId=${user.id}`
                );
                if (!clientResponse.ok) throw new Error('Failed to fetch client details.');
                const clientData = await clientResponse.json();
                setClientDetails(clientData);

                const animalsResponse = await fetch(
                    `http://localhost:8080/api/animals/byClientId?clientId=${clientData.id}`
                );
                if (!animalsResponse.ok) throw new Error('Failed to fetch animals.');
                const animalsData = await animalsResponse.json();
                setAnimals(animalsData);

                const locationsResponse = await fetch('http://localhost:8080/api/locations');
                if (!locationsResponse.ok) throw new Error('Failed to fetch locations.');
                const locationsData = await locationsResponse.json();
                setLocations(locationsData);

                setLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error);
                setErrorMessage('Eroare la încărcarea datelor.');
                setLoading(false);
            }
        };

        fetchData();
    }, [user, navigate]);

    const handleSearchRooms = async () => {
        const today = new Date();
        const selectedDate = new Date(checkIn);

        today.setHours(0, 0, 0, 0);
        selectedDate.setHours(0, 0, 0, 0);

        if (selectedDate < today) {
            setErrorCheckIn('Data de check-in nu poate fi mai mică decât data curentă.');
            return;
        } else {
            setErrorCheckIn(''); // Resetăm eroarea dacă data este validă
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/rooms/available?locationId=${selectedLocation}&checkIn=${checkIn}&checkOut=${checkOut}`
            );
            if (!response.ok) throw new Error('Failed to fetch available rooms.');
            const data = await response.json();
            setAvailableRooms(data);
        } catch (error) {
            console.error('Error fetching rooms:', error);
            setErrorMessage('Eroare la căutarea camerelor disponibile.');
        }
    };

    const handleBooking = async () => {
        if (selectedRooms.length !== selectedAnimals.length) {
            setErrorMessage('Numărul de camere selectate trebuie să fie egal cu numărul de animale selectate.');
            return;
        }

        try {
            const reservationRequest = {
                clientId: clientDetails.id,
                locationId: selectedLocation,
                checkIn,
                checkOut,
                animalIds: selectedAnimals,
                roomIds: selectedRooms,
            };

            const response = await fetch('http://localhost:8080/api/reservations/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(reservationRequest),
            });

            if (!response.ok) throw new Error('Failed to create reservation.');

            alert('Rezervarea a fost creată cu succes!');
            navigate('/history');
        } catch (error) {
            console.error('Error creating reservation:', error);
            setErrorMessage('Eroare la crearea rezervării.');
        }
    };

    if (loading) return <p>Se încarcă...</p>;

    return (
        <div className="booking-page">
            <h1>Rezervare</h1>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            {errorCheckIn && <p className="error-message">{errorCheckIn}</p>}  {/* Afișăm eroarea de check-in */}
            <div className="step">
                <h2>Selectează Locația și Animalele</h2>
                <label>Locație:</label>
                <select onChange={(e) => setSelectedLocation(e.target.value)} value={selectedLocation}>
                    <option value="">Selectează locația</option>
                    {locations.map((loc) => (
                        <option key={loc.id} value={loc.id}>
                            {loc.name}
                        </option>
                    ))}
                </select>
                <label>Animale:</label>
                <div className="card-list">
                    {animals.map((animal) => (
                        <div className="card" key={animal.id}>
                            <h3>{animal.name}</h3>
                            <p>{animal.age} ani - {animal.gender}</p>
                            <label>
                                <input
                                    type="checkbox"
                                    value={animal.id}
                                    onChange={(e) => {
                                        const id = parseInt(e.target.value);
                                        if (e.target.checked) {
                                            setSelectedAnimals([...selectedAnimals, id]);
                                        } else {
                                            setSelectedAnimals(selectedAnimals.filter((a) => a !== id));
                                        }
                                    }}
                                />
                                Selectează
                            </label>
                        </div>
                    ))}
                </div>
                <label>Check-in:</label>
                <input type="date" value={checkIn} onChange={(e) => setCheckIn(e.target.value)} />
                <label>Check-out:</label>
                <input type="date" value={checkOut} onChange={(e) => setCheckOut(e.target.value)} />
                <button className="cta-button" onClick={handleSearchRooms}>
                    Caută Camere
                </button>
            </div>

            {availableRooms.length > 0 && (
                <div className="step">
                    <h2>Selectează Camerele</h2>
                    <div className="card-list">
                        {availableRooms.map((room) => (
                            <div className="card" key={room.id}>
                                <h3>{room.roomType}</h3>
                                <p>{room.price} RON</p>
                                <label>
                                    <input
                                        type="checkbox"
                                        value={room.id}
                                        onChange={(e) => {
                                            const id = parseInt(e.target.value);
                                            if (e.target.checked) {
                                                setSelectedRooms([...selectedRooms, id]);
                                            } else {
                                                setSelectedRooms(selectedRooms.filter((r) => r !== id));
                                            }
                                        }}
                                    />
                                    Selectează
                                </label>
                            </div>
                        ))}
                    </div>
                    <button className="secondary-button" onClick={handleBooking}>
                        Finalizează Rezervarea
                    </button>
                </div>
            )}
        </div>
    );
}

export default BookingPage;
