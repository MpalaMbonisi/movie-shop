import React, { useEffect, useState } from 'react';
import { getMoviesByGenre, getMyList } from '../api/api';
import { useNavigate } from 'react-router-dom';
import './dashboard.css';
import Header from '../components/Header';

const Dashboard = () => {
  const [moviesByGenre, setMoviesByGenre] = useState({});
  const navigate = useNavigate();

  // Retrieve accountId from localStorage
  const accountId = localStorage.getItem('accountId');

  useEffect(() => {
    const fetchMovies = async () => {
      const genres = ['drama', 'crime', 'action', 'animation', 'science fiction'];
      const genreMovies = {};

      try {
        // Fetch "My List" movies
        const myListRes = await getMyList(accountId);
        const myListMovieIds = myListRes.data.map(movie => movie.movie.id);

        // Fetch movies by genre and filter out movies in "My List"
        for (let i = 0; i < genres.length; i++) {
          const genreId = i + 1; 
          const res = await getMoviesByGenre(genreId);
          const filteredMovies = res.data.filter(movie => !myListMovieIds.includes(movie.id));
          genreMovies[genres[i]] = filteredMovies;
        }

        setMoviesByGenre(genreMovies);
      } catch (error) {
        console.error('Error fetching movies or My List:', error);
      }
    };

    fetchMovies();
  }, [accountId]);

  return (
    <div className="dashboard-page">
      <Header accountId={accountId} />
      {/* Welcome Section */}
      <div className="welcome-section">
        <div className="dashboard-overlay">
          <img src="/images/welcome.jpg" alt="Welcome" className="welcome-image" />
          <div className="content">
            <h1 className="title">Welcome to MovieHub!</h1>
            <p className="subtitle">
              Discover the latest blockbusters and timeless classics in stunning 4K HD. Your ultimate destination for movie entertainment.
            </p>
          </div>
        </div>
      </div>

      {/* Movies by Genre */}
      <div className="movies-section">
        {Object.keys(moviesByGenre).map((genre) => (
          <div key={genre} className="dashboard-genre-section">
            <h3 className="dashboard-genre-title">{genre.charAt(0).toUpperCase() + genre.slice(1)}</h3>
            <div className="dashboard-movie-grid">
              {moviesByGenre[genre].map((movie) => (
                <div key={movie.id} className="dashboard-card">
                  <img
                    src={movie.poster}
                    className="dashboard-card-img-top"
                    onClick={() => navigate(`/movie/${movie.id}`)}
                    alt={movie.title}
                  />
                  <div className="dashboard-card-body">
                    <h4 className="dashboard-release-year">{movie.releaseYear}</h4>
                    <h5 className="dashboard-card-title">{movie.title}</h5>
                    <p className="dashboard-price"><strong>Price:</strong> {movie.price} zł</p>
                    <button
                      className="dashboard-buyBtn"
                      onClick={() => navigate(`/movie/${movie.id}`)}
                    >
                      Buy
                    </button>
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