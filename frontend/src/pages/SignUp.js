import React, { useState } from 'react';
import { createAccount } from '../api/api';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './signUp.css'; // Import the CSS file for styling

const SignUp = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      const res = await createAccount({ email, password });
      login(res.data);
      navigate('/dashboard');
    } catch (err) {
      alert('Error creating account. Please check your input.');
    }
  };

  return (
    <div className="signup-page">
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

      {/* Sign-Up Form */}
      <div className="signup-container">
        <h2 className="signup-title">Create Account</h2>
        <form onSubmit={handleSignup}>
          <input
            className="form-control"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            className="form-control"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="btn-signup">Sign Up</button>
        </form>
        <p className="login-question">
          Already have an account? <Link to="/login" className="login-link">Login</Link>
        </p>
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default SignUp;