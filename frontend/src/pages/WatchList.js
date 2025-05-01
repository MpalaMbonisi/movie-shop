import React, { useEffect, useState } from 'react';
import { getWatchList, getMyList, getCartItems, addToCart, removeFromWatchList } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import './watchlist.css'; // Import the CSS file for styling

const WatchList = () => {
  const { account } = useAuth();
  const [watchlist, setWatchlist] = useState([]);
  const [myList, setMyList] = useState([]);
  const [cartList, setCartList] = useState([]);
  const navigate = useNavigate();

  const refresh = () => {
    getWatchList(account.id).then(res => setWatchlist(res.data));
    getCartItems(account.id).then(res => setCartList(res.data));
  };

  useEffect(() => {
    // Fetch watchlist, cart, and my list
    getWatchList(account.id).then(res => setWatchlist(res.data));
    getCartItems(account.id).then(res => setCartList(res.data));
    getMyList(account.id).then(res => setMyList(res.data));
  }, [account.id]);

  const handleRemove = async (movieId) => {
    await removeFromWatchList(account.id, movieId);
    refresh();
  };

  const handleBuy = async (movieId) => {
    const isInCart = cartList.some(item => item.movie.id === movieId);

    if (!isInCart) {
      // Add to cart if not already in the cart
      await addToCart(account.id, movieId);
    }

    // Redirect to the cart page
    navigate('/cart');
  };

  const isMovieInMyList = (movieId) => {
    return myList.some(movie => movie.id === movieId);
  };

  return (
    <div>
      <Header accountId={account.id} />
      <div className="watchlist-container">
        <h2>Your Watchlist</h2>
        {watchlist.length === 0 ? (
          <p className="watch-list-empty-text">Your watchlist is empty😢.</p>
        ) : (
          <div className="watchlist-items">
            {watchlist.map(item => (
              <div key={item.id} className="watchlist-item">
                {/* Poster redirects to movie details page */}
                <img
                  src={item.movie.poster}
                  alt={item.movie.title}
                  className="watchlist-item-poster"
                  onClick={() => navigate(`/movie/${item.movie.id}`)}
                  style={{ cursor: 'pointer' }} // Add pointer cursor for better UX
                />
                <div className="watchlist-item-details">
                  <h4>{item.movie.title}</h4>
                  <div className="item-details">
                    <div>
                      <p><strong>Year:</strong> {item.movie.releaseYear}</p>
                      <p class="watchlist-cast"><strong>Cast:</strong> {item.movie.actors}</p>
                    </div>
                    <div className="watchlist-align-left">
                      <p><strong>Duration:</strong> {item.movie.duration}</p>
                      <p><strong>Genre:</strong> {item.movie.genre.genre}</p>
                    </div>
                  </div>
                  <div className="buttons-grid"> 
                    <button onClick={() => handleRemove(item.movie.id)} className="watchlist-remove-btn">
                      Remove
                    </button> 
                    {isMovieInMyList(item.movie.id) ? (
                      <button
                        onClick={() => window.open(item.movie.trailer, '_blank')}
                        className="watchlist-btn"
                      >
                        Watch
                      </button>
                    ) : (
                      <button
                        onClick={() => handleBuy(item.movie.id)}
                        className="watchlist-btn"
                      >
                        Buy
                      </button>
                    )}
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default WatchList;