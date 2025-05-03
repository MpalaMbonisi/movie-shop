import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { authenticateAccount } from '../api/api';
import './login.css'; // Import the CSS file for styling

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false); // State to toggle password visibility
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Clear any existing token and accountId
      localStorage.removeItem('token');
      localStorage.removeItem('accountId');
  
      // Authenticate and get the JWT token
      const authResponse = await axios.post('http://localhost:8080/account/authenticate', { email, password });
      const token = authResponse.headers['authorization']?.split(' ')[1];
      if (!token) {
        throw new Error('Token not found in response');
      }
  
      // Store the token
      localStorage.setItem('token', token);
  
      // Fetch the accountId
      const accountId = await authenticateAccount(email); // Directly get the accountId
      localStorage.setItem('accountId', accountId);
  
      // Log the accountId for debugging
      console.log('Logged in with accountId:', accountId);
  
      // Navigate to the dashboard
      login({ token, accountId });
      navigate('/dashboard');
    } catch (err) {
      console.error('Error during login:', err);
      if (err.response) {
        const { status, data } = err.response;
        if (status === 404) {
          alert(data || 'Email does not exist in our records.');
        } else if (status === 403) {
          alert(data || 'Your Json Web Token is invalid.');
        } else if (status === 400) {
          alert(data || 'Bad Request');
        } else {
          alert('An unexpected error occurred. Please try again.');
        }
      } else {
        alert('Unable to connect to the server. Please check your network.');
      }
    }
  };

  return (
    <div className="login-page">
      {/* Top Left Logo Button */}
      <button className="logo" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </button>

      {/* Centered Logo */}
      <div className="logo-centered" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </div>

      {/* Login Form */}
      <div className="login-container">
        <h2 className="login-title">Login</h2>
        <form onSubmit={handleLogin}>
          <input
            type="email"
            className="form-control"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <div className="password-container">
            <input
              type={showPassword ? 'text' : 'password'} // Toggle input type
              className="form-control"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <button
              type="button"
              className="btn-show-password"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? 'Hide' : 'Show'}
            </button>
          </div>
          <button className="btn-login">Login</button>
        </form>
        <p className="signup-question">
          Don't have an account? <Link to="/signup" className="signup-link">Sign Up</Link>
        </p>
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Login;