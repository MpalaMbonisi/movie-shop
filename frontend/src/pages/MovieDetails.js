import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getMovies } from '../api/api';
import { BASE_URL } from '../api/config';
import Header from '../components/Header';

const MovieDetails = () => {
  const { movieId } = useParams(); // Get the movie ID from the URL
  console.log('Movie ID:', movieId); // Log the movieId to verify

  const [movie, setMovie] = useState(null);
  const [movies, setMovies] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the movie details
    const fetchMovieDetails = async () => {
      try {
        const res = await fetch(`${BASE_URL}/movie/${movieId}`);
        if (!res.ok) {
          throw new Error('Failed to fetch movie details');
        }
        const data = await res.json();
        setMovie(data); // Use the flat JSON response directly
      } catch (err) {
        console.error('Failed to fetch movie details:', err);
      }
    };

    // Fetch the list of movies
    getMovies().then(res => setMovies(res.data));

    fetchMovieDetails();
  }, [movieId]);

  if (!movie) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Header />
      <div className="container mt-5">
        <div className="row">
          <div className="col-md-4">
            <img src={movie.posterUrl} alt={movie.title} className="img-fluid" />
          </div>
          <div className="col-md-8">
            <h2>{movie.title}</h2>
            <p><strong>Genre:</strong> {movie.genre}</p>
            <p><strong>Price:</strong> ${movie.price}</p>
            <a href={movie.trailerUrl} target="_blank" rel="noopener noreferrer" className="btn btn-primary me-2">Watch Trailer</a>
            <a href={movie.movieUrl} target="_blank" rel="noopener noreferrer" className="btn btn-success">Watch Movie</a>
          </div>
        </div>

        <hr className="my-5" />

        <h3>Other Movies</h3>
        <div className="row">
          {movies.map(m => (
            <div key={m.id} className="col-md-3 mb-4">
              <div className="card">
                <img src={m.posterUrl} className="card-img-top" alt={m.title} />
                <div className="card-body">
                  <h5 className="card-title">{m.title}</h5>
                  <p><strong>Price:</strong> ${m.price}</p>
                  <button className="btn btn-primary" onClick={() => navigate(`/movie/${m.id}`)}>View Details</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MovieDetails;