import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { addToCart, addToWatchList, getCartItems, getWatchList } from '../api/api'; // Import necessary API methods
import { BASE_URL } from '../api/config';
import Header from '../components/Header';
import './movieDetails.css';

const MovieDetails = () => {
  const { movieId } = useParams();
  const [movie, setMovie] = useState(null);
  const [movies, setMovies] = useState([]);
  const [cartList, setCartList] = useState([]);
  const [watchList, setWatchList] = useState([]);
  const navigate = useNavigate();
  const accountId = 1; // Replace with the actual account ID (e.g., from authentication)

  useEffect(() => {
    const fetchMovieDetails = async () => {
      try {
        const res = await fetch(`${BASE_URL}/movie/${movieId}`);
        if (!res.ok) {
          throw new Error('Failed to fetch movie details');
        }
        const data = await res.json();
        setMovie(data);

        // Fetch related movies after movie details are loaded
        const relatedRes = await fetch(`${BASE_URL}/movie/genre/${data.genre.id}`);
        const relatedData = await relatedRes.json();
        const filtered = relatedData.filter(m => m.id !== parseInt(movieId));
        setMovies(filtered);
      } catch (err) {
        console.error('Failed to fetch movie details:', err);
      }
    };

    const fetchCartAndWatchList = async () => {
      try {
        const cartRes = await getCartItems(accountId);
        setCartList(cartRes.data);

        const watchListRes = await getWatchList(accountId);
        setWatchList(watchListRes.data);
      } catch (err) {
        console.error('Failed to fetch cart or watchlist:', err);
      }
    };

    fetchMovieDetails();
    fetchCartAndWatchList();
  }, [movieId, accountId]);

  const handleAddToCart = async () => {
    try {
      await addToCart(accountId, movieId); // Call the addToCart API method
      const userChoice = window.confirm(
        "Movie added to cart! Do you want to continue shopping for more movies?"
      );
      if (userChoice) {
        navigate('/dashboard'); // Redirect to the dashboard
      } else {
        navigate('/cart'); // Redirect to the cart
      }
    } catch (error) {
      alert("Failed to add movie to cart. Please try again.");
    }
  };
  const handleAddToWatchList = async () => {
    try {
      // Call the addToWatchList API method
      await addToWatchList(accountId, movieId);

      // Refresh watchlist
      const watchListRes = await getWatchList(accountId);
      setWatchList(watchListRes.data);

      // Redirect to the watchlist page
      navigate('/watchlist');
    } catch (error) {
      console.error('Failed to add movie to watchlist:', error);
      alert('Failed to add movie to watchlist. Please try again.');
    }
  };

  const handlePurchase = (movieId) => {
    navigate(`/movie/${movieId}`);
    window.location.reload(); // Refresh the page after navigation
  };

  const isInCart = () => cartList.some(item => item.movie.id === parseInt(movieId));
  const isInWatchList = () => watchList.some(item => item.movie.id === parseInt(movieId));

  if (!movie) {
    return <div>Loading...</div>;
  }

  return (
    <div
      className="movie-details-page"
      style={{
        backgroundImage: `url(${movie?.poster})`,
        backgroundSize: 'contain',
        backgroundRepeat: 'repeat',
        backgroundPosition: 'right',
        backgroundColor: 'black',
        height: '100vh',
      }}
    >
      <div className="overlayout">
        <Header accountId={accountId} />
        <div className="movie-details-content">
          {/* Poster Section */}
          <div className="col-md-4 poster-section">
            <img src={movie.poster} alt={movie.title} className="img-fluid" />
            <p className="price"><strong>Price:</strong> {movie.price} zł</p>
          </div>

          {/* Description Section */}
          <div className="col-md-8 description-section">
            <h1 className="movie-title">{movie.title}</h1>
            <div className="button-group">
              <button className="btn" onClick={() => window.open(movie.trailer, '_blank')}>
                <i className="fas fa-play"></i> Watch Trailer
              </button>
              {!isInCart() && (
                <button className="btn" onClick={handleAddToCart}>
                  <i className="fas fa-cart-plus"></i> Add to Cart
                </button>
              )}
              {!isInWatchList() && (
                <button className="btn" onClick={handleAddToWatchList}>
                  <i className="fas fa-heart"></i> Add to Watchlist
                </button>
              )}
            </div>
            <div className="overview">
              <h3>Overview</h3>
              <p>{movie.plot}</p>
            </div>
            <div className="details-grid">
              <div>
                <strong>Duration:</strong> {movie.duration}
              </div>
              <div>
                <strong>Language:</strong> {movie.movieLanguage}
              </div>
              <div>
                <strong>Cast:</strong> {movie.actors}
              </div>
              <div>
                <strong>Genre:</strong> {movie.genre.genre}
              </div>
            </div>
          </div>
        </div>
        {/* Movies You May Also Like Section */}
        <div className="movie-section">
          <h2 className="section-title">You May Also Like</h2>
          <div className="movie-details-movie-grid">
            {movies.map(movie => (
              <div key={movie.id} className="movie-details-card">
                <img 
                    src={movie.poster} 
                    className="movie-details-card-img-top"
                    onClick={() => handlePurchase(movie.id)}
                     alt={movie.title} />
                <div className="movie-details-card-body">
                  <h4 className="movie-details-release-year">{movie.releaseYear}</h4>
                  <h5 className="movie-details-card-title">{movie.title}</h5>
                  <p className="movie-details-card-price"><strong>Price:</strong> {movie.price}zł</p>
                  <button className="movie-details-buyBtn" onClick={() => handlePurchase(movie.id)}>Buy</button>
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
    </div>
  );
};

export default MovieDetails;