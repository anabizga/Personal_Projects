import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import '../styles/SignupPage.css';

function SignupPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const [message, setMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleSignup = (e) => {
        e.preventDefault();
        const formattedDate = new Date(birthDate).toISOString().split('T')[0]; // Format "yyyy-MM-dd"
        console.log({
            username,
            password,
            firstName,
            lastName,
            birthDate: formattedDate,
        });
        fetch('http://localhost:8080/api/users/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({username, password, firstName, lastName, birthDate: formattedDate}),

        })
            .then((response) => {
                if (response.ok) {
                    setMessage('Account created successfully!');
                    setTimeout(() => navigate('/login'), 2000);
                } else {
                    return response.json().then((data) => {
                        setErrorMessage(data.error || 'Failed to create account');
                        throw new Error(data.error || 'Failed to create account');
                    });
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                setErrorMessage(error.message);
            });
    };

    return (
        <div className="signup-page">
            <h1>Create Account</h1>
            <form className="signup-form" onSubmit={handleSignup}>
                <div className="form-group">
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        type="text"
                        id="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        type="text"
                        id="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="birthDate">Birth Date:</label>
                    <input
                        type="date"
                        id="birthDate"
                        value={birthDate}
                        onChange={(e) => setBirthDate(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="signup-button">Sign Up</button>
            </form>
            {errorMessage && <p className="error-message" style={{color: 'red'}}>{errorMessage}</p>}
            {message && <p className="signup-message">{message}</p>}
        </div>
    );
}

export default SignupPage;
