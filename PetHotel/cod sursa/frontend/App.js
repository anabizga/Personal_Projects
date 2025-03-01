import React, {useState} from 'react';
import './App.css';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import HomePage from './components/Homepage';
import ReviewsPage from './components/ReviewsPage';
import LocationsPage from './components/LocationsPage';
import LogInPage from "./components/LogInPage";
import SignupPage from './components/SignupPage';
import HistoryPage from "./components/HistoryPage";
import NewReviewPage from "./components/NewReviewPage";
import ClientAccountManagementPage from "./components/ClientAccountManagementPage";
import AdminAccountManagementPage from "./components/AdminAccountManagementPage";
import AnimalPage from "./components/AnimalPage";
import MedicalInfoPage from "./components/MedicalInfoPage";
import UserManagementPage from "./components/UserManagementPage";
import RoomsPage from "./components/RoomsPage";
import AdminReservationPage from "./components/AdminReservationPage";
import CheckInOutPage from "./components/CheckInOutPage";
import AvailableRoomsPage from "./components/AvailableRoomsPage";
import BookingPage from "./components/BookingPage";

function App() {
    const [user, setUser] = useState(null);

    const handleLogin = (userData) => {
        setUser(userData);
    };

    const handleLogout = () => {
        setUser(null);
    };

    return (
        <Router>
            <Routes>
                <Route
                    path="/"
                    element={<HomePage
                        user={user}
                        handleLogout={handleLogout}
                    />}
                />
                <Route path="/reviews" element={<ReviewsPage/>}/>
                <Route path="/locations" element={<LocationsPage user={user}/>}/>
                <Route
                    path="/login"
                    element={<LogInPage
                        setUser={handleLogin}
                    />}
                />
                <Route path="/signup" element={<SignupPage/>}/>
                <Route path="/history" element={<HistoryPage user={user} />} />
                <Route path="/reviews/new" element={<NewReviewPage user={user} />} />
                <Route path="/clientAccount" element={<ClientAccountManagementPage user={user} />} />
                <Route path="/adminAccount" element={<AdminAccountManagementPage user={user} />} />
                <Route path="/animals" element={<AnimalPage user={user} />} />
                <Route path="/medical-info/:animalId" element={<MedicalInfoPage user={user} />} />
                <Route path="/usersManagement" element={<UserManagementPage user={user} />} />
                <Route path="/locations/:locationId" element={<RoomsPage user={user} />} />
                <Route path="/admin-reservations" element={<AdminReservationPage user={user} />} />
                <Route path="/admin-checkinout" element={<CheckInOutPage user={user} />} />
                <Route path="/available-rooms" element={<AvailableRoomsPage />} />
                <Route path="/book" element={<BookingPage user={user} />} />
            </Routes>
        </Router>
    );
}

export default App;
