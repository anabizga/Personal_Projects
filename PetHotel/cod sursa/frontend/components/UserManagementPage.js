import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/UserManagementPage.css";

function UserManagementPage({ user }) {
    const navigate = useNavigate();
    const [users, setUsers] = useState([]);
    const [locations, setLocations] = useState([]);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [promoteModal, setPromoteModal] = useState(false);
    const [addAdminModal, setAddAdminModal] = useState(false);
    const [selectedUserId, setSelectedUserId] = useState(null);
    const [selectedLocationId, setSelectedLocationId] = useState("");
    const [newAdmin, setNewAdmin] = useState({
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        locationId: "",
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/users")
            .then((response) => response.json())
            .then((data) => setUsers(data))
            .catch((error) => console.error("Error fetching users:", error));

        fetch("http://localhost:8080/api/locations")
            .then((response) => response.json())
            .then((data) => setLocations(data))
            .catch((error) => console.error("Error fetching locations:", error));
    }, []);

    const handleDeleteUser = (userId) => {
        fetch(`http://localhost:8080/api/users/${userId}`, {
            method: "DELETE",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to delete user.");
                }
                setUsers((prevUsers) => prevUsers.filter((u) => u.id !== userId));
                setSuccessMessage("Utilizatorul a fost șters cu succes.");
                setTimeout(() => setSuccessMessage(""), 3000);
            })
            .catch((error) => {
                setErrorMessage("Eroare la ștergerea utilizatorului.");
                setTimeout(() => setErrorMessage(""), 3000);
                console.error("Error deleting user:", error);
            });
    };

    const handlePromoteToAdmin = (e) => {
        e.preventDefault();

        if (!selectedUserId || !selectedLocationId) {
            setErrorMessage("Selectați un utilizator și o locație.");
            setTimeout(() => setErrorMessage(""), 3000);
            return;
        }

        fetch(`http://localhost:8080/api/users/${selectedUserId}/promoteToAdmin?locationId=${selectedLocationId}`, {
            method: "PUT",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to promote user to admin.");
                }
                return response.json();
            })
            .then(() => {
                setUsers((prevUsers) =>
                    prevUsers.map((u) =>
                        u.id === selectedUserId
                            ? { ...u, userType: "Admin" }
                            : u
                    )
                );
                setSuccessMessage("Utilizatorul a fost promovat la admin cu succes.");
                setTimeout(() => setSuccessMessage(""), 3000);
                setPromoteModal(false);
            })
            .catch((error) => {
                setErrorMessage("Eroare la promovarea utilizatorului la admin.");
                setTimeout(() => setErrorMessage(""), 3000);
                console.error("Error promoting user:", error);
            });
    };

    const handleAddAdmin = (e) => {
        e.preventDefault();

        if (!newAdmin.username || !newAdmin.password || !newAdmin.firstName || !newAdmin.lastName || !newAdmin.locationId) {
            setErrorMessage("Completați toate câmpurile.");
            setTimeout(() => setErrorMessage(""), 3000);
            return;
        }

        fetch("http://localhost:8080/api/admins", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username: newAdmin.username,
                password: newAdmin.password,
                firstName: newAdmin.firstName,
                lastName: newAdmin.lastName,
                locationId: newAdmin.locationId,
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to add admin.");
                }
                return response.json();
            })
            .then((data) => {
                setUsers([...users, data]);
                setSuccessMessage("Admin adăugat cu succes.");
                setTimeout(() => setSuccessMessage(""), 3000);
                setAddAdminModal(false);
                setNewAdmin({
                    username: "",
                    password: "",
                    firstName: "",
                    lastName: "",
                    locationId: "",
                });
            })
            .catch((error) => {
                setErrorMessage("Eroare la adăugarea adminului.");
                setTimeout(() => setErrorMessage(""), 3000);
                console.error("Error adding admin:", error);
            });
    };

    return (
        <div className="user-management-page">
            <h1>Gestionare Utilizatori</h1>

            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            <button onClick={() => setAddAdminModal(true)}>Adaugă Admin</button>

            <ul>
                {users.map((u) => (
                    <li key={u.id} className="user-item">
                        <p><strong>Username:</strong> {u.username}</p>
                        <p><strong>Tip Utilizator:</strong> {u.userType}</p>
                        {u.userType !== "SuperAdmin" && (
                            <div className="button-group">
                                {u.userType === "Client" && (
                                    <>
                                        <button
                                            onClick={() => {
                                                setSelectedUserId(u.id);
                                                setPromoteModal(true);
                                            }}
                                        >
                                            Promovează la Admin
                                        </button>
                                    </>
                                )}
                                <button onClick={() => handleDeleteUser(u.id)}>Șterge</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>

            {promoteModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Promovează la Admin</h2>
                        <form onSubmit={handlePromoteToAdmin}>
                            <label>
                                Selectați locația:
                                <select
                                    value={selectedLocationId}
                                    onChange={(e) => setSelectedLocationId(e.target.value)}
                                    required
                                >
                                    <option value="">Selectează o locație</option>
                                    {locations.map((location) => (
                                        <option key={location.id} value={location.id}>
                                            {location.name}
                                        </option>
                                    ))}
                                </select>
                            </label>
                            <div className="modal-buttons">
                                <button type="submit" className="save-button">Salvează</button>
                                <button
                                    type="button"
                                    className="cancel-button"
                                    onClick={() => setPromoteModal(false)}
                                >
                                    Anulează
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {addAdminModal && (
                <div className="modal">
                    <div className="modal-content">
                        <h2>Adaugă Admin</h2>
                        <form onSubmit={handleAddAdmin}>
                            <label>
                                Username:
                                <input
                                    type="text"
                                    value={newAdmin.username}
                                    onChange={(e) => setNewAdmin({ ...newAdmin, username: e.target.value })}
                                    required
                                />
                            </label>
                            <label>
                                Parola:
                                <input
                                    type="password"
                                    value={newAdmin.password}
                                    onChange={(e) => setNewAdmin({ ...newAdmin, password: e.target.value })}
                                    required
                                />
                            </label>
                            <label>
                                Nume:
                                <input
                                    type="text"
                                    value={newAdmin.firstName}
                                    onChange={(e) => setNewAdmin({ ...newAdmin, firstName: e.target.value })}
                                    required
                                />
                            </label>
                            <label>
                                Prenume:
                                <input
                                    type="text"
                                    value={newAdmin.lastName}
                                    onChange={(e) => setNewAdmin({ ...newAdmin, lastName: e.target.value })}
                                    required
                                />
                            </label>
                            <label>
                                Selectați locația:
                                <select
                                    value={newAdmin.locationId}
                                    onChange={(e) => setNewAdmin({ ...newAdmin, locationId: e.target.value })}
                                    required
                                >
                                    <option value="">Selectează o locație</option>
                                    {locations.map((location) => (
                                        <option key={location.id} value={location.id}>
                                            {location.name}
                                        </option>
                                    ))}
                                </select>
                            </label>
                            <div className="modal-buttons">
                                <button type="submit" className="save-button">Adaugă</button>
                                <button
                                    type="button"
                                    className="cancel-button"
                                    onClick={() => setAddAdminModal(false)}
                                >
                                    Anulează
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default UserManagementPage;
