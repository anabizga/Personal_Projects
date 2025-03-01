import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import '../styles/LoginPage.css';

function LogInPage({setUser}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();

        fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({username, password}),
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Invalid credentials');
                }
            })
            .then((data) => {
                console.log('Login successful:', data);
                setErrorMessage('');
                setUser(data);
                navigate('/');
            })
            .catch((error) => {
                console.error('Error:', error);
                setErrorMessage('Invalid username or password');
            });
    };
    const handleSignupRedirect = () => {
        navigate('/signup');
    };

    return (
        <div className="login-page">
            <h1>Login</h1>
            <form className="login-form" onSubmit={handleLogin}>
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
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                <div className="button-group">
                    <button type="submit" className="login-button">Login</button>
                    <button onClick={handleSignupRedirect} className="signup-button">Create Account</button>
                </div>
            </form>
        </div>
    );
}

export default LogInPage;