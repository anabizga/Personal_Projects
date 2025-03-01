import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import '../styles/AccountManagementPage.css';

function ClientAccountManagementPage({user}) {
    const navigate = useNavigate();
    const [contacts, setContacts] = useState([]);
    const [newContact, setNewContact] = useState({type: '', info: ''});
    const [clientId, setClientId] = useState(null);
    const [clientDetails, setClientDetails] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [editingUsername, setEditingUsername] = useState(false);
    const [editingPassword, setEditingPassword] = useState(false);
    const [newUsername, setNewUsername] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [editingDetails, setEditingDetails] = useState(false);
    const [editDetails, setEditDetails] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
    });

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
                throw new Error('Failed to fetch client details');
            })
            .then((data) => {
                setClientDetails(data);
                setEditDetails({
                    firstName: data.firstName || '',
                    lastName: data.lastName || '',
                    dateOfBirth: data.dateOfBirth ? data.dateOfBirth.split('T')[0] : '',
                });
            })
            .catch((error) => {
                console.error('Error fetching client details:', error);
            });
    }, [user, navigate]);

    useEffect(() => {
        if (!user) return;

        fetch(`http://localhost:8080/api/contacts/byUserId?userId=${user.id}`)
            .then((response) => response.json())
            .then((data) => setContacts(data))
            .catch((error) => console.error('Error fetching contacts:', error));
    }, [user]);

    const handleAddContact = (e) => {
        e.preventDefault();

        if (newContact.type === 'Email' && !/\S+@\S+\.\S+/.test(newContact.info)) {
            setErrorMessage('Adresa de email nu este validă');
            return;
        }
        if (newContact.type === 'Phone' && !/^\d{10}$/.test(newContact.info)) {
            setErrorMessage('Numărul de telefon trebuie să aibă 10 cifre');
            return;
        }

        fetch('http://localhost:8080/api/contacts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                type: newContact.type,
                info: newContact.info,
                user: {id: user.id},
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to add contact');
                }
                return response.text().then((text) => (text ? JSON.parse(text) : {}));
            })
            .then((contact) => {
                setSuccessMessage('Contact adăugat cu succes');
                setTimeout(() => setSuccessMessage(''), 3000);
                setContacts([...contacts, contact]);
                setNewContact({type: '', info: ''});
            })
            .catch((error) => {
                setErrorMessage('Eroare la adăugarea contactului');
                setTimeout(() => setErrorMessage(''), 3000);
                console.error('Error adding contact:', error);
            });


    };

    const handleDeleteContact = (contactId) => {
        fetch(`http://localhost:8080/api/contacts/${contactId}`, {
            method: 'DELETE',
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Failed to delete contact: ${response.status}`);
                }
                setContacts((prevContacts) => prevContacts.filter(contact => contact.id !== contactId));
                setSuccessMessage('Contact șters cu succes.');
                setTimeout(() => setSuccessMessage(''), 3000);
            })
            .catch((error) => {
                setErrorMessage('Eroare la ștergerea contactului.');
                setTimeout(() => setErrorMessage(''), 3000);
                console.error('Error deleting contact:', error);
            });
    };

    const handleUpdateUsername = (e) => {
        e.preventDefault();

        if (!user?.id) {
            setErrorMessage('ID-ul utilizatorului nu este valid.');
            return;
        }

        fetch(`http://localhost:8080/api/users/${user.id}/credentials`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: newUsername, // Trimitem doar username
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to update username');
                }
                return response.json();
            })
            .then(() => {
                setSuccessMessage('Username actualizat cu succes.');
                setTimeout(() => setSuccessMessage(''), 3000);

                // Actualizare username în obiectul `user`
                user.username = newUsername; // Actualizează valoarea direct în starea `user`
                setEditingUsername(false);
            })
            .catch((error) => {
                setErrorMessage('Eroare la actualizarea username-ului.');
                setTimeout(() => setErrorMessage(''), 3000);
                console.error('Error updating username:', error);
            });
    };

    const handleUpdatePassword = (e) => {
        e.preventDefault();

        if (!user?.id) {
            setErrorMessage('ID-ul utilizatorului nu este valid.');
            return;
        }

        fetch(`http://localhost:8080/api/users/${user.id}/credentials`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                password: newPassword,
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to update password');
                }
                return response.json();
            })
            .then(() => {
                setSuccessMessage('Parola actualizată cu succes.');
                setTimeout(() => setSuccessMessage(''), 3000);

                setEditingPassword(false);
            })
            .catch((error) => {
                setErrorMessage('Eroare la actualizarea parolei.');
                setTimeout(() => setErrorMessage(''), 3000);
                console.error('Error updating password:', error);
            });
    };

    const handleUpdateDetails = (e) => {
        e.preventDefault();

        fetch(`http://localhost:8080/api/clients/${clientDetails.id}/details`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(editDetails),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to update client details');
                }
                return response.json();
            })
            .then(() => {
                setSuccessMessage('Informațiile au fost actualizate cu succes.');
                setTimeout(() => setSuccessMessage(''), 3000);
                setEditingDetails(false);

                setClientDetails((prev) => ({
                    ...prev,
                    ...editDetails,
                }));
            })
            .catch((error) => {
                setErrorMessage('Eroare la actualizarea informațiilor.');
                setTimeout(() => setErrorMessage(''), 3000);
                console.error('Error updating client details:', error);
            });
    };

    return (
        <div className="account-management-page">
            <h1>Gestionare Cont</h1>

            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            <div className="user-details">
                <h2>Informații Utilizator</h2>
                {clientDetails ? (
                    <>
                        {!editingDetails && !editingUsername && !editingPassword ? (
                            <>
                                <p><strong>Nume:</strong> {clientDetails.firstName} {clientDetails.lastName}</p>
                                <p><strong>Data nașterii:</strong> {clientDetails.dateOfBirth}</p>
                                <p><strong>Username:</strong> {user.username}</p>
                                <p><strong>Tip utilizator:</strong> {user.userType}</p>
                                <button
                                    style={{marginRight: '10px'}}
                                    onClick={() => setEditingDetails(true)}
                                >
                                    Editează Detalii
                                </button>
                                <button
                                    style={{marginRight: '10px'}}
                                    onClick={() => setEditingUsername(true)}
                                >
                                    Editează Username
                                </button>
                                <button onClick={() => setEditingPassword(true)}>Editează Parola</button>
                            </>
                        ) : null}

                        {editingDetails && (
                            <form onSubmit={handleUpdateDetails}>
                                <label>
                                    Nume:
                                    <input
                                        type="text"
                                        value={editDetails.firstName}
                                        onChange={(e) =>
                                            setEditDetails({...editDetails, firstName: e.target.value})
                                        }
                                        required
                                    />
                                </label>
                                <label>
                                    Prenume:
                                    <input
                                        type="text"
                                        value={editDetails.lastName}
                                        onChange={(e) =>
                                            setEditDetails({...editDetails, lastName: e.target.value})
                                        }
                                        required
                                    />
                                </label>
                                <label>
                                    Data nașterii:
                                    <input
                                        type="date"
                                        value={editDetails.dateOfBirth}
                                        onChange={(e) =>
                                            setEditDetails({...editDetails, dateOfBirth: e.target.value})
                                        }
                                        required
                                    />
                                </label>
                                <button type="submit">Salvează</button>
                                <button type="button" onClick={() => setEditingDetails(false)}>Anulează</button>
                            </form>
                        )}

                        {editingUsername && (
                            <form onSubmit={handleUpdateUsername}>
                                <label>
                                    Username:
                                    <input
                                        type="text"
                                        value={newUsername || user.username}
                                        onChange={(e) => setNewUsername(e.target.value)}
                                        required
                                    />
                                </label>
                                <button type="submit">Salvează</button>
                                <button type="button" onClick={() => setEditingUsername(false)}>Anulează</button>
                            </form>
                        )}

                        {editingPassword && (
                            <form onSubmit={handleUpdatePassword}>
                                <label>
                                    Parola:
                                    <input
                                        type="password"
                                        value={newPassword}
                                        onChange={(e) => setNewPassword(e.target.value)}
                                        required
                                    />
                                </label>
                                <button type="submit">Salvează</button>
                                <button type="button" onClick={() => setEditingPassword(false)}>Anulează</button>
                            </form>
                        )}
                    </>
                ) : (
                    <p>Se încarcă detaliile utilizatorului...</p>
                )}
            </div>


            <div className="contacts-section">
                <h2>Contacte</h2>
                <ul>
                    {contacts.map((contact) => (
                        <li key={contact.id}
                            style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                            <strong>{contact.type}:</strong> {contact.info}
                            <button onClick={() => handleDeleteContact(contact.id)}
                                    style={{marginLeft: '10px'}}>Șterge
                            </button>
                        </li>
                    ))}
                </ul>

                <form onSubmit={handleAddContact}>
                    <h3>Adaugă Contact</h3>
                    <label htmlFor="contact-type">Tip:</label>
                    <select
                        id="contact-type"
                        value={newContact.type}
                        onChange={(e) => setNewContact({...newContact, type: e.target.value})}
                        required
                    >
                        <option value="">Selectează tipul</option>
                        <option value="Email">Email</option>
                        <option value="Phone">Telefon</option>
                    </select>
                    <label htmlFor="contact-info">Informație:</label>
                    <input
                        id="contact-info"
                        type="text"
                        value={newContact.info}
                        onChange={(e) => setNewContact({...newContact, info: e.target.value})}
                        required
                    />
                    <button type="submit">Adaugă Contact</button>
                </form>
            </div>
        </div>
    );
}

export default ClientAccountManagementPage;