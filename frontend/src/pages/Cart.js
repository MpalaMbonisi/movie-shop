import React, { useEffect, useState } from 'react';
import { getCartItems, removeFromCart } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import './cart.css'; // Import the CSS file for styling

const Cart = () => {
  const { account } = useAuth();
  const [cart, setCart] = useState([]);
  const navigate = useNavigate();

  const refresh = () => {
    getCartItems(account.id).then(res => setCart(res.data));
  };

  useEffect(() => {
    refresh();
  }, []);

  const handleRemove = async (movieId) => {
    await removeFromCart(account.id, movieId);
    refresh();
  };

  const calculateTotalPrice = () => {
    return cart
          .reduce((total, item) => total + Number(item.movie.price), 0)
          .toFixed(2); // Ensure two decimal places
  };

  return (
    <div>
      <Header />
      <div className="cart-container">
        <h2>Your Cart</h2>
        {cart.length === 0 ? (
          <p class="empty-cart-text">Your cart is empty😢.</p>
        ) : (
          <>
            <div className="cart-items">
              {cart.map(item => (
                <div key={item.id} className="cart-item">
                  <img
                  src={item.movie.poster}
                  alt={item.movie.title}
                  className="cart-item-poster"
                  onClick={() => navigate(`/movie/${item.movie.id}`)}
                  style={{ cursor: 'pointer' }} // Add pointer cursor for better UX
                />
                  <div className="cart-item-details">
                    <div class="item-details"> 
                      <div>
                        <p><strong>Year:</strong> {item.movie.releaseYear}</p>
                        <p><strong>Price:</strong> {item.movie.price} zł</p>
                        <h4>{item.movie.title}</h4>
                      </div>
                      <div class="align-left">
                        <p><strong>Duration:</strong> {item.movie.duration}</p>
                        <p><strong>Genre:</strong> {item.movie.genre.genre}</p>
                        <button onClick={() => handleRemove(item.movie.id)} className="btn">
                          Remove
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
            <div className="cart-summary">
              <h3>Total Price: {calculateTotalPrice()} zł</h3>
              <button className="btn" onClick={() => navigate('/checkout')}>
                Proceed to Checkout
              </button>
            </div>
          </>
        )}
      </div>
      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
    
  );
};

export default Cart;