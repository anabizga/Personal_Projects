import React, { useState, useEffect } from 'react';
import '../styles/LocationsPage.css';
import { useNavigate } from 'react-router-dom';

function LocationsPage({ user }) {
    const [locations, setLocations] = useState([]);
    const isSuperAdmin = user?.userType === 'SuperAdmin';
    const [formState, setFormState] = useState({
        id: null,
        name: '',
        address: '',
        phoneNumber: '',
    });
    const [isEditing, setIsEditing] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8080/api/locations')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setLocations(data);
            })
            .catch((error) => console.error('Error fetching locations:', error));
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState({ ...formState, [name]: value });
    };

    const handleAddOrUpdateLocation = (e) => {
        e.preventDefault();

        const method = isEditing ? 'PUT' : 'POST';
        const url = isEditing
            ? `http://localhost:8080/api/locations/${formState.id}`
            : 'http://localhost:8080/api/locations';

        fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: formState.name,
                address: formState.address,
                phoneNumber: formState.phoneNumber,
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add/update location');
                }
                return response.json();
            })
            .then((newLocation) => {
                if (isEditing) {
                    setLocations((prev) =>
                        prev.map((loc) => (loc.id === newLocation.id ? newLocation : loc))
                    );
                } else {
                    setLocations((prev) => [...prev, newLocation]);
                }
                setFormState({ id: null, name: '', address: '', phoneNumber: '' });
                setIsEditing(false);
            })
            .catch((error) => console.error('Error adding/updating location:', error));
    };

    const handleEditClick = (location) => {
        setFormState(location);
        setIsEditing(true);
    };

    const handleDeleteLocation = (locationId) => {
        fetch(`http://localhost:8080/api/locations/${locationId}`, {
            method: 'DELETE',
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to delete location');
                }
                setLocations((prevLocations) => prevLocations.filter((loc) => loc.id !== locationId));
            })
            .catch((error) => console.error('Error deleting location:', error));
    };

    const handleDetailsClick = (locationId) => {
        navigate(`/locations/${locationId}`);
    };

    return (
        <div className="locations-page">
            <h1>Locații</h1>

            {isSuperAdmin && (
                <div className="add-location-box">
                    <form onSubmit={handleAddOrUpdateLocation} className="location-form">
                        <h2>{isEditing ? 'Editează Locație' : 'Adaugă Locație'}</h2>
                        <input
                            type="text"
                            name="name"
                            placeholder="Nume"
                            value={formState.name}
                            onChange={handleInputChange}
                            required
                        />
                        <input
                            type="text"
                            name="address"
                            placeholder="Adresă"
                            value={formState.address}
                            onChange={handleInputChange}
                            required
                        />
                        <input
                            type="text"
                            name="phoneNumber"
                            placeholder="Telefon"
                            value={formState.phoneNumber}
                            onChange={handleInputChange}
                            required
                        />
                        <button type="submit">
                            {isEditing ? 'Salvează Modificările' : 'Adaugă Locație'}
                        </button>
                    </form>
                </div>
            )}

            <div className="locations-list">
                {locations.length > 0 ? (
                    locations.map((location) => (
                        <div key={location.id} className="location-card">
                            <h2>{location.name}</h2>
                            <p>Adresa: {location.address}</p>
                            <p>Telefon: {location.phoneNumber}</p>
                            <div className="location-buttons">
                                <button
                                    className="details-button"
                                    onClick={() => handleDetailsClick(location.id)}
                                >
                                    Detalii
                                </button>

                                {isSuperAdmin && (
                                    <>
                                        <button
                                            className="edit-location-button"
                                            onClick={() => handleEditClick(location)}
                                        >
                                            Editează
                                        </button>
                                        <button
                                            className="delete-location-button"
                                            onClick={() => handleDeleteLocation(location.id)}
                                        >
                                            Șterge
                                        </button>
                                    </>
                                )}
                            </div>
                        </div>
                    ))
                ) : (
                    <p>Nu există locații disponibile.</p>
                )}
            </div>
        </div>
    );
}

export default LocationsPage;