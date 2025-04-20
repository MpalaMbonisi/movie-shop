import React, { useEffect, useState } from 'react';
import { getMoviesByGenre } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
  const [moviesByGenre, setMoviesByGenre] = useState({});
  const { account } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const genres = ['drama', 'crime', 'action', 'history', 'science fiction', 'romance', 'fantasy'];
    const fetchMovies = async () => {
      const genreMovies = {};
      for (let i = 0; i < genres.length; i++) {
        const genreId = i + 1; // Genre IDs start at 1
        const res = await getMoviesByGenre(genreId);
        genreMovies[genres[i]] = res.data;
      }
      setMoviesByGenre(genreMovies);
    };
    fetchMovies();
  }, []);

  return (
    <div className="dashboard-page">
      {/* Header */}
      <header className="dashboard-header">
        <div className="logo" onClick={() => navigate('/dashboard')}>
          <span className="movie">Movie</span>
          <span className="hub">Hub</span>
        </div>
        <h2 className="dashboard-title">Dashboard</h2>
      </header>

      {/* Welcome Section */}
      <div className="welcome-section">
        <div className="overlay">
          <img src="/images/welcome.jpg" alt="Welcome" className="welcome-image" />
          <div className="content text-center">
            <h1 className="title">Welcome to MovieHub Dashboard!</h1>
            <p className="subtitle">Explore movies by genre and build your collection.</p>
          </div>
        </div>
      </div>

      {/* Movies by Genre */}
      <div className="movies-section">
        {Object.keys(moviesByGenre).map((genre) => (
          <div key={genre} className="genre-section">
            <h3 className="genre-title">{genre.charAt(0).toUpperCase() + genre.slice(1)}</h3>
            <div className="movie-grid">
              {moviesByGenre[genre].map((movie) => (
                <div key={movie.id} className="card">
                  <img src={movie.posterUrl} className="card-img-top" alt={movie.title} />
                  <div className="card-body">
                    <h5 className="card-title">{movie.title}</h5>
                    <p><strong>Price:</strong> ${movie.price}</p>
                    <button className="btn btn-primary">Add to Watchlist</button>
                    <button className="btn btn-secondary">Add to Cart</button>
                    <button className="btn btn-outline-primary">View Details</button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Dashboard;