import React, { useEffect, useState } from 'react';
import { getMoviesByGenre, getMyList } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import './dashboard.css';
import Header from '../components/Header';

const Dashboard = () => {
  const [moviesByGenre, setMoviesByGenre] = useState({});
  const { account } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMovies = async () => {
      const genres = ['drama', 'crime', 'action', 'animation', 'science fiction'];
      const genreMovies = {};

      try {
        // Fetch "My List" movies
        const myListRes = await getMyList(account.id);
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
  }, [account.id]);

  return (
    <div className="dashboard-page">
      <Header accountId={account.id} />
      {/* Welcome Section */}
      <div className="welcome-section">
        <div className="overlay">
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
          <div key={genre} className="genre-section">
            <h3 className="genre-title">{genre.charAt(0).toUpperCase() + genre.slice(1)}</h3>
            <div className="movie-grid">
              {moviesByGenre[genre].map((movie) => (
                <div key={movie.id} className="card">
                  <img
                    src={movie.poster}
                    className="card-img-top"
                    onClick={() => navigate(`/movie/${movie.id}`)}
                    alt={movie.title}
                  />
                  <div className="card-body">
                    <h4 className="release-year">{movie.releaseYear}</h4>
                    <h5 className="card-title">{movie.title}</h5>
                    <p className="price"><strong>Price:</strong> {movie.price} zł</p>
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