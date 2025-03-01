import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import '../styles/AnimalPage.css';

function AnimalPage({user}) {
    const navigate = useNavigate();
    const [animals, setAnimals] = useState([]);
    const [clientDetails, setClientDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');
    const [editingAnimal, setEditingAnimal] = useState(null);
    const [editDetails, setEditDetails] = useState({name: '', age: '', gender: ''});
    const [addAnimalModal, setAddAnimalModal] = useState(false);
    const [newAnimal, setNewAnimal] = useState({
        name: '',
        age: '',
        gender: 'Mascul',
    });

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }

        fetch(`http://localhost:8080/api/clients/byUserId?userId=${user.id}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch client details');
                }
                return response.json();
            })
            .then((data) => {
                setClientDetails(data);
                return data.id;
            })
            .then((clientId) => {
                return fetch(`http://localhost:8080/api/animals/byClientId?clientId=${clientId}`);
            })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch animals');
                }
                return response.json();
            })
            .then((data) => {
                setAnimals(data);
                setLoading(false);
            })
            .catch((error) => {
                setErrorMessage('Eroare la încărcarea datelor.');
                console.error('Error:', error);
                setLoading(false);
            });
    }, [user, navigate]);

    const handleDeleteAnimal = (animalId) => {
        console.log("Attempting to delete animal with ID:", animalId);

        fetch(`http://localhost:8080/api/animals/${animalId}`, {
            method: 'DELETE',
        })
            .then((response) => {
                if (!response.ok) {
                    return response.json().then((error) => {
                        console.error("Backend error:", error);
                        throw new Error(error.error || 'Failed to delete animal');
                    });
                }
                console.log("Animal deleted successfully");
                setAnimals((prevAnimals) => prevAnimals.filter((animal) => animal.id !== animalId));
            })
            .catch((error) => {
                setErrorMessage('Eroare la ștergerea animalului.');
                console.error('Error deleting animal:', error);
            });
    };


    const handleEditAnimal = (animal) => {
        setEditingAnimal(animal.id);
        setEditDetails({name: animal.name, age: animal.age, gender: animal.gender});
    };

    const handleUpdateAnimal = (e) => {
        e.preventDefault();

        fetch(`http://localhost:8080/api/animals/${editingAnimal}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(editDetails),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to update animal');
                }
                return response.json();
            })
            .then(() => {
                setAnimals((prevAnimals) =>
                    prevAnimals.map((animal) =>
                        animal.id === editingAnimal
                            ? {...animal, ...editDetails}
                            : animal
                    )
                );
                setEditingAnimal(null);
            })
            .catch((error) => {
                setErrorMessage('Eroare la actualizarea animalului.');
                console.error('Error updating animal:', error);
            });
    };

    const handleAddAnimal = (e) => {
        e.preventDefault();

        if (!clientDetails) {
            setErrorMessage('Client details not found.');
            return;
        }

        fetch('http://localhost:8080/api/animals', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...newAnimal,
                age: parseInt(newAnimal.age, 10), // Ensure age is sent as a number
                client: {id: clientDetails.id},
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add animal');
                }
                return response.json();
            })
            .then((data) => {
                setAnimals((prevAnimals) => [...prevAnimals, data]);
                setNewAnimal({name: '', age: '', gender: 'Mascul'});
                setAddAnimalModal(false);
            })
            .catch((error) => {
                setErrorMessage('Eroare la adăugarea animalului.');
                console.error('Error adding animal:', error);
            });
    };

    if (loading) {
        return <p>Se încarcă datele...</p>;
    }

    return (
        <div className="animal-page">
            <h1>Animalele Mele</h1>

            {/* Add Animal Button */}
            <button
                className="add-animal-button"
                onClick={() => setAddAnimalModal(true)}
            >
                Adaugă Animal
            </button>

            {addAnimalModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Adaugă Animal</h2>
                        <form onSubmit={handleAddAnimal}>
                            <label>
                                Nume:
                                <input
                                    type="text"
                                    value={newAnimal.name}
                                    onChange={(e) =>
                                        setNewAnimal({...newAnimal, name: e.target.value})
                                    }
                                    required
                                />
                            </label>
                            <label>
                                Vârstă:
                                <input
                                    type="number"
                                    value={newAnimal.age}
                                    onChange={(e) =>
                                        setNewAnimal({...newAnimal, age: e.target.value})
                                    }
                                    required
                                />
                            </label>
                            <label>
                                Gen:
                                <select
                                    value={newAnimal.gender}
                                    onChange={(e) =>
                                        setNewAnimal({...newAnimal, gender: e.target.value})
                                    }
                                >
                                    <option value="Mascul">Mascul</option>
                                    <option value="Femelă">Femelă</option>
                                </select>
                            </label>
                            <div className="modal-buttons">
                                <button type="submit" className="save-button">
                                    Salvează
                                </button>
                                <button
                                    type="button"
                                    onClick={() => setAddAnimalModal(false)}
                                    className="cancel-button"
                                >
                                    Anulează
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {animals.length > 0 ? (
                <ul>
                    {animals.map((animal) => (
                        <li key={animal.id} className="animal-item">
                            {editingAnimal === animal.id ? (
                                <form onSubmit={handleUpdateAnimal}>
                                    <label>
                                        Nume:
                                        <input
                                            type="text"
                                            value={editDetails.name}
                                            onChange={(e) =>
                                                setEditDetails({...editDetails, name: e.target.value})
                                            }
                                            required
                                        />
                                    </label>
                                    <label>
                                        Vârstă:
                                        <input
                                            type="number"
                                            value={editDetails.age}
                                            onChange={(e) =>
                                                setEditDetails({...editDetails, age: e.target.value})
                                            }
                                            required
                                        />
                                    </label>
                                    <label>
                                        Gen:
                                        <select
                                            value={editDetails.gender}
                                            onChange={(e) =>
                                                setEditDetails({
                                                    ...editDetails,
                                                    gender: e.target.value,
                                                })
                                            }
                                            required
                                        >
                                            <option value="Mascul">Mascul</option>
                                            <option value="Femelă">Femelă</option>
                                        </select>
                                    </label>
                                    <button type="submit">Salvează</button>
                                    <button type="button" onClick={() => setEditingAnimal(null)}>
                                        Anulează
                                    </button>
                                </form>
                            ) : (
                                <>
                                    <p>
                                        <strong>Nume:</strong> {animal.name}
                                    </p>
                                    <p>
                                        <strong>Vârstă:</strong> {animal.age}
                                    </p>
                                    <p>
                                        <strong>Gen:</strong> {animal.gender}
                                    </p>
                                    <div className="button-group">
                                        <button onClick={() => handleEditAnimal(animal)}>Editează</button>
                                        <button onClick={() => handleDeleteAnimal(animal.id)}>
                                            Șterge
                                        </button>
                                        <button
                                            onClick={() => navigate(`/medical-info/${animal.id}`)}
                                        >
                                            Detalii
                                        </button>
                                    </div>
                                </>
                            )}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Nu există animale înregistrate pentru acest client.</p>
            )}
        </div>
    );
}

export default AnimalPage;