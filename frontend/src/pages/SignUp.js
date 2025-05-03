import React, { useState } from 'react';
import { registerAccount } from '../api/api'; // Use the correct API function for signup
import { useNavigate, Link } from 'react-router-dom';
import './signUp.css'; // Import the CSS file for styling

const SignUp = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false); // State to toggle password visibility
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      // Call the API to register the account
      await registerAccount({ email, password });
      alert('Account created successfully! Please log in.');
      navigate('/login'); // Redirect to the login page after successful signup
    } catch (err) {
      // Handle specific error messages from the backend
      if (err.response) {
        const { status, data } = err.response;
        if (status === 400) {
          alert(data || 'Invalid input. Please check your details.'); // Handle bad request
        } else if (status === 409) {
          alert(data || 'An account with this email already exists.'); // Handle conflict
        } else {
          alert('An unexpected error occurred. Please try again.');
        }
      } else {
        alert('Unable to connect to the server. Please check your network.');
      }
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