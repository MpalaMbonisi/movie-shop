import React, { useState } from 'react';
import { getAccount } from '../api/api';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Login.css'; // Import the CSS file for styling

const Login = () => {
  const [accountId, setAccountId] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await getAccount(accountId);
      login(res.data);
      navigate('/dashboard');
    } catch (err) {
      alert('Invalid account ID');
    }
  };

  return (
    <div className="login-page">
      {/* Top Left Logo Button */}
      <button className="logo-button" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </button>

      {/* Centered Logo */}
      <div className="logo-centered">
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </div>

      {/* Login Form */}
      <div className="login-container">
        <h2 className="login-title">Login</h2>
        <form onSubmit={handleLogin}>
          <input
            type="number"
            className="form-control mb-3"
            placeholder="Account ID"
            value={accountId}
            onChange={(e) => setAccountId(e.target.value)}
            required
          />
          <button className="btn btn-primary btn-login">Login</button>
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