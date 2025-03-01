import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/MedicalInfoPage.css";

function MedicalInfoPage({ user }) {
    const { animalId } = useParams(); // Get animalId from the route parameter
    const navigate = useNavigate();
    const [animalDetails, setAnimalDetails] = useState(null); // To store animal information
    const [medicalInfo, setMedicalInfo] = useState([]);
    const [newMedicalInfo, setNewMedicalInfo] = useState({
        medicalDescription: "",
        medicalType: "",
    });
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    useEffect(() => {
        if (!user) {
            navigate("/login");
            return;
        }

        fetch(`http://localhost:8080/api/animals/${animalId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch animal information.");
                }
                return response.json();
            })
            .then((data) => setAnimalDetails(data))
            .catch((error) => {
                setErrorMessage("Eroare la încărcarea datelor animalului.");
                console.error("Error:", error);
            });

        fetch(`http://localhost:8080/api/medicalInfo/byAnimalId?animalId=${animalId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch medical information.");
                }
                return response.json();
            })
            .then((data) => {
                setMedicalInfo(data)
            })
            .catch((error) => {
                setErrorMessage("Eroare la încărcarea datelor medicale.");
                if (medicalInfo.length===0) {
                    setErrorMessage("Nu exista date medicale pentru acest animal.");
                }
                console.error("Error:", error);
            });
    }, [animalId, navigate, user]);

    const handleAddMedicalInfo = (e) => {
        e.preventDefault();

        fetch("http://localhost:8080/api/medicalInfo", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                ...newMedicalInfo,
                animal: { id: animalId },
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    return response.json().then((error) => {
                        throw new Error(error.error || "Failed to add medical information.");
                    });
                }
                return response.json();
            })
            .then((data) => {
                setSuccessMessage("Informația medicală a fost adăugată cu succes.");
                setErrorMessage("");
                setTimeout(() => setSuccessMessage(""), 3000);

                setMedicalInfo([...medicalInfo, data]);
                setNewMedicalInfo({ medicalDescription: "", medicalType: "" });
            })
            .catch((error) => {
                setErrorMessage("Eroare la adăugarea informațiilor medicale.");
                console.error("Error adding medical info:", error);
            });
    };


    const handleDeleteMedicalInfo = (infoId) => {
        fetch(`http://localhost:8080/api/medicalInfo/${infoId}`, {
            method: "DELETE",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to delete medical information.");
                }
                setMedicalInfo((prevInfo) =>
                    prevInfo.filter((info) => info.id !== infoId)
                );
                setSuccessMessage("Informația medicală a fost ștearsă cu succes.");
                setTimeout(() => setSuccessMessage(""), 3000);
            })
            .catch((error) => {
                setErrorMessage("Eroare la ștergerea informațiilor medicale.");
                console.error("Error deleting medical info:", error);
            });
    };

    return (
        <div className="medical-info-page">
            <h1>Informații Medicale</h1>

            {successMessage && <p className="success-message">{successMessage}</p>}
            {errorMessage && <p className="error-message">{errorMessage}</p>}

            {animalDetails && (
                <div className="animal-info-box">
                    <h2>Informații Animal</h2>
                    <p><strong>Nume:</strong> {animalDetails.name}</p>
                    <p><strong>Vârstă:</strong> {animalDetails.age}</p>
                    <p><strong>Gen:</strong> {animalDetails.gender}</p>
                </div>
            )}

            <ul>
                {medicalInfo.map((info) => (
                    <li key={info.id} className="medical-info-item">
                        <p><strong>Tip:</strong> {info.medicalType}</p>
                        <p><strong>Descriere:</strong> {info.medicalDescription}</p>
                        <button onClick={() => handleDeleteMedicalInfo(info.id)}>Șterge</button>
                    </li>
                ))}
            </ul>

            <form onSubmit={handleAddMedicalInfo} className="add-medical-info-form">
                <h2>Adaugă Informație Medicală</h2>
                <label>
                    Tip:
                    <textarea
                        value={newMedicalInfo.medicalType}
                        onChange={(e) =>
                            setNewMedicalInfo({
                                ...newMedicalInfo,
                                medicalType: e.target.value,
                            })
                        }
                        required
                    />
                </label>
                <label>
                    Descriere:
                    <textarea
                        value={newMedicalInfo.medicalDescription}
                        onChange={(e) =>
                            setNewMedicalInfo({
                                ...newMedicalInfo,
                                medicalDescription: e.target.value,
                            })
                        }
                        required
                    />
                </label>
                <button type="submit">Adaugă</button>
            </form>
        </div>
    );
}

export default MedicalInfoPage;
