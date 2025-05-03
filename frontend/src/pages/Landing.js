import React, { useEffect, useState } from 'react';
import { getMovies } from '../api/api';
import { useNavigate } from 'react-router-dom';
import './landing.css';

const Landing = () => {
  const [movies, setMovies] = useState([]);
  const [animationMovies, setAnimationMovies] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem('token');
    localStorage.removeItem('accountId');
    localStorage.removeItem('authToken'); 

    // Fetch all movies
    getMovies().then(res => setMovies(res.data));

    // Fetch action movies (genre ID = 4) 
    fetch('http://localhost:8080/movie/genre/4')
      .then(response => response.json())
      .then(data => setAnimationMovies(data));
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
            <h1 className="title">Best movie store platform in the world.</h1>
            <p className="subtitle">Purchase your favorites movies. Best prices. 4k bluray.</p>
            <div className="buttons">
              <button className="loginBtn" onClick={() => navigate('/login')}>Login</button>
              <button className="signUpBtn" onClick={() => navigate('/signup')}>Sign Up</button>
            </div>
          </div>
        </div>
      </div>

      {/* Animation Movies Section */}
      <div className="movie-section">
        <h2 className="section-title">Animation</h2>
        <div className="landing-movie-grid">
          {animationMovies.map(movie => (
            <div key={movie.id} className="card">
              <img src={movie.poster} className="card-img-top" alt={movie.title} />
              <div className="card-body">
                <h5 className="card-title">{movie.title}</h5>
                <p className='card-price'><strong>Price:</strong> {movie.price} zł</p>
                <button className="landing-buyBtn" onClick={() => navigate('/signup')}>Buy</button>
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