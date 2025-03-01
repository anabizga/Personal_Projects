import React, { useState, useEffect } from 'react';
import '../styles/RoomsPage.css';
import { useParams } from 'react-router-dom';

function RoomsPage({ user }) {
    const { locationId } = useParams();
    const [rooms, setRooms] = useState([]);
    const [locationName, setLocationName] = useState('');
    const isSuperAdmin = user?.userType === 'SuperAdmin'; // Check if the user is SuperAdmin
    const [formState, setFormState] = useState({
        id: null,
        roomType: '',
        price: '',
    });
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        fetch(`http://localhost:8080/api/rooms/byLocationId?locationId=${locationId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setRooms(data);
            })
            .catch((error) => console.error('Error fetching rooms:', error));

        // Fetch location details to get the name
        fetch(`http://localhost:8080/api/locations/${locationId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setLocationName(data.name);
            })
            .catch((error) => console.error('Error fetching location details:', error));
    }, [locationId]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState({ ...formState, [name]: value });
    };

    const handleAddOrUpdateRoom = (e) => {
        e.preventDefault();

        const method = isEditing ? 'PUT' : 'POST';
        const url = isEditing
            ? `http://localhost:8080/api/rooms/${formState.id}`
            : 'http://localhost:8080/api/rooms';

        fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                roomType: formState.roomType,
                price: formState.price,
                location: { id: locationId },
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add/update room');
                }
                return response.json();
            })
            .then((newRoom) => {
                if (isEditing) {
                    setRooms((prev) =>
                        prev.map((room) => (room.id === newRoom.id ? newRoom : room))
                    );
                } else {
                    setRooms((prev) => [...prev, newRoom]);
                }
                setFormState({ id: null, roomType: '', price: '' });
                setIsEditing(false);
            })
            .catch((error) => console.error('Error adding/updating room:', error));
    };

    const handleEditClick = (room) => {
        setFormState(room);
        setIsEditing(true);
    };

    const handleDeleteRoom = (roomId) => {
        fetch(`http://localhost:8080/api/rooms/${roomId}`, {
            method: 'DELETE',
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to delete room');
                }
                setRooms((prevRooms) => prevRooms.filter((room) => room.id !== roomId));
            })
            .catch((error) => console.error('Error deleting room:', error));
    };

    return (
        <div className="rooms-page">
            <h1>Camere pentru Locația {locationName}</h1>

            {isSuperAdmin && (
                <div className="add-room-box">
                    <form onSubmit={handleAddOrUpdateRoom} className="room-form">
                        <h2>{isEditing ? 'Editează Cameră' : 'Adaugă Cameră'}</h2>
                        <input
                            type="text"
                            name="roomType"
                            placeholder="Tip cameră"
                            value={formState.roomType}
                            onChange={handleInputChange}
                            required
                        />
                        <input
                            type="number"
                            name="price"
                            placeholder="Preț"
                            value={formState.price}
                            onChange={handleInputChange}
                            required
                        />
                        <button type="submit">
                            {isEditing ? 'Salvează Modificările' : 'Adaugă Cameră'}
                        </button>
                    </form>
                </div>
            )}

            <div className="rooms-list">
                {rooms.length > 0 ? (
                    rooms.map((room) => (
                        <div key={room.id} className="room-card">
                            <h2>{room.roomType}</h2>
                            <p>Preț: {room.price} RON</p>
                            <div className="room-buttons" style={{display: 'flex', gap: '10px'}}>
                                {isSuperAdmin && (
                                    <>
                                        <button className="edit-room-button"
                                                onClick={() => handleEditClick(room)}
                                        >
                                            Editează
                                        </button>
                                        <button
                                            className="delete-room-button"
                                            onClick={() => handleDeleteRoom(room.id)}
                                        >
                                            Șterge
                                        </button>
                                    </>
                                )}
                            </div>
                        </div>
                    ))
                ) : (
                    <p>Nu există camere disponibile pentru această locație.</p>
                )}
            </div>
        </div>
    );
}

export default RoomsPage;
