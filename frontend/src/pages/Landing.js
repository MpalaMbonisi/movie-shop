import React, { useEffect, useState } from 'react';
import { getMovies } from '../api/api';
import { useNavigate } from 'react-router-dom';
import './Landing.css';

const Landing = () => {
  const [movies, setMovies] = useState([]);
  const [actionMovies, setActionMovies] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch all movies
    getMovies().then(res => setMovies(res.data));

    // Fetch action movies (genre ID = 1)
    fetch('http://localhost:8080/movie/genre/1')
      .then(response => response.json())
      .then(data => setActionMovies(data));
  }, []);

  return (
    <div className="landing-page">
      {/* Logo */}
      <div className="logo" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </div>

      {/* Video Section */}
      <div className="video-section">
        <video autoPlay muted loop className="background-video">
          <source src="/videos/landing.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
        <div className="overlay">
          <div className="content text-center">
            <h1 className="title">Welcome to MovieHub 🎬</h1>
            <p className="subtitle">Stream your favorites. Create your collection.</p>
            <div className="buttons">
              <button className="btn btn-primary m-2" onClick={() => navigate('/login')}>Login</button>
              <button className="btn btn-outline-success m-2" onClick={() => navigate('/signup')}>Sign Up</button>
            </div>
          </div>
        </div>
      </div>

      {/* Action Movies Section */}
      <div className="movie-section">
        <h2 className="section-title">Action Movies</h2>
        <div className="movie-grid container">
          {actionMovies.map(movie => (
            <div key={movie.id} className="card">
              <img src={movie.posterUrl} className="card-img-top" alt={movie.title} />
              <div className="card-body">
                <h5 className="card-title">{movie.title}</h5>
                <button className="btn btn-primary" onClick={() => navigate(`/movie/${movie.id}`)}>View Details</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Landing;