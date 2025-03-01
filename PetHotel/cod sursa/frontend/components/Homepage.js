import React from 'react';
import '../styles/Homepage.css';
import pets from '../assets/pets.jpg';
import {useNavigate} from 'react-router-dom';

function HomePage({user, handleLogout}) {
    const navigate = useNavigate();

    return (
        <div className="homepage">
            <nav className="navbar">
                <div className="logo">Pet Hotel</div>
                <div className="nav-links">
                    {user?.userType === 'Client' && (
                        <>
                            <span onClick={() => navigate('/clientAccount')} style={{cursor: 'pointer'}}>
                                Contul meu
                            </span>
                            <span onClick={() => navigate('/animals')} style={{cursor: 'pointer'}}>
                                Animalele mele
                            </span>
                        </>
                    )}
                    {user?.userType === 'Admin' && (
                        <>
                            <span onClick={() => navigate('/adminAccount')} style={{cursor: 'pointer'}}>
                                Contul meu
                            </span>
                        </>
                    )}
                    <span onClick={() => navigate('/reviews')} style={{cursor: 'pointer'}}>Recenzii</span>
                    <span onClick={() => navigate('/locations')} style={{cursor: 'pointer'}}>Locații</span>
                    {user ? (
                        <span onClick={handleLogout} style={{cursor: 'pointer'}}>Logout</span>
                    ) : (
                        <span onClick={() => navigate('/login')} style={{cursor: 'pointer'}}>Login</span>
                    )}
                </div>
            </nav>
            <header className="hero-section">
                <div className="hero-content">
                    <img src={pets} alt="Pets" className="hero-image"/>
                    <div className="hero-text">
                        <h1>Bun venit pe PetHotel.ro</h1>
                        <p>
                            Suntem cel mai nou hotel pentru animăluțul tău de companie din țară.
                            Cazare All Inclusive, supraveghere medicală, interacțiune zilnică
                            și joacă cu personalul nostru.
                        </p>
                        <div className="button-group">
                            {user?.userType === 'Client' && (
                                <>
                                    <button className="cta-button"
                                            onClick={() => navigate('/book')}
                                    >
                                        Rezervă acum
                                    </button>
                                    <button className="cta-button"
                                            onClick={() => navigate('/available-rooms')}
                                    >
                                        Vezi camere disponibile
                                    </button>
                                    <button
                                        className="cta-button"
                                        onClick={() => navigate('/history')}
                                    >
                                        Istoric Rezervări
                                    </button>
                                </>
                            )}
                            {user?.userType === 'Admin' && (
                                <>
                                    <button
                                        className="cta-button"
                                        onClick={() => navigate('/admin-reservations')}
                                        style={{cursor: 'pointer'}}
                                    >
                                        Rezervări
                                    </button>
                                    <button
                                        className="cta-button"
                                        onClick={() => navigate('/admin-checkinout')}
                                    >
                                        Check-In/Check-Out
                                    </button>
                                </>

                            )}
                            {user?.userType === 'SuperAdmin' && (
                                <>
                                    <button
                                        className="cta-button"
                                        onClick={() => navigate('/usersManagement')}
                                    >
                                        Utilizatori
                                    </button>
                                </>
                            )}
                        </div>
                    </div>
                </div>
            </header>
        </div>
    );
}

export default HomePage;
