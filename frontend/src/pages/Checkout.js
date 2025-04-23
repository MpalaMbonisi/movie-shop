import React, { useEffect, useState } from 'react';
import { getCartItems, orderMovie, clearCart } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import './checkout.css'; // Import the CSS file for styling

const Checkout = () => {
  const { account } = useAuth();
  const [items, setItems] = useState([]);
  const [selectedPayment, setSelectedPayment] = useState('');
  const [isLoading, setIsLoading] = useState(false); // State to manage loading animation
  const navigate = useNavigate();

  useEffect(() => {
    getCartItems(account.id).then(res => setItems(res.data));
  }, [account.id]);

  const calculateTotalPrice = () => {
    return items
      .reduce((total, item) => total + Number(item.movie.price), 0)
      .toFixed(2); // Ensure two decimal places
  };

  const handleBuy = async () => {
    if (!selectedPayment) {
      alert('Please select a payment option.');
      return;
    }

    setIsLoading(true); // Show loading animation

    try {
      // Add all movies to "My List"
      for (const item of items) {
        await orderMovie(account.id, item.movie.id);
      }

      // Clear the cart after successful purchase
      await clearCart(account.id);

      // Redirect to My List after a short delay
      setTimeout(() => {
        setIsLoading(false); // Hide loading animation
        navigate('/mylist');
      }, 4500); // Adjust delay as needed
    } catch (err) {
      setIsLoading(false); // Hide loading animation on error
      alert('Failed to complete purchase. Please try again.');
    }
  };

  return (
    <div className="checkout-container">
      {/* Top Left Logo Button */}
      <button className="logo" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </button>

      {/* Centered Logo */}
      <div className="logo-centered">
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </div>

      <div className="checkout-content">
        {isLoading ? (
          // Show loading animation while processing
          <div className="loading-container">
            <p>Processing your transaction...</p>
            <iframe
              src="https://lottie.host/embed/05ccf8e4-0358-4324-bf8e-a6c676058c61/49WCcMLZwJ.lottie"
              style={{
                width: '400px',
                height: '400px',
                border: 'none',
                margin: '0 auto',
                display: 'block',
              }}
              title="Loading Animation"
            ></iframe>
          </div>
        ) : (
          <section className="checkout-split">
            <div>
              <h2>Checkout</h2>
              <div className="checkout-items">
                {items.map(item => (
                  <div key={item.id} className="checkout-item">
                    <img
                      src={item.movie.poster}
                      alt={item.movie.title}
                      className="checkout-item-poster"
                    />
                    <div className="checkout-details"> 
                      <h4>{item.movie.title}</h4>
                      <div className="checkout-item-details">
                          <p><strong>Year:</strong> {item.movie.releaseYear}</p>
                          <p><strong>Duration:</strong> {item.movie.duration}</p>
                          <p><strong>Genre:</strong> {item.movie.genre.genre}</p>
                          <p><strong>Cast:</strong> {item.movie.actors}</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {/* Total Price */}
              <div className="checkout-summary">
                <h3>Total Price: {calculateTotalPrice()} zł</h3>
              </div>
            </div>
            <div>
              {/* Payment Options */}
              <h2>Payment Options</h2>
              <div className="payment-options">
                <label className="container">
                  Visa
                  <input
                    type="radio"
                    name="payment"
                    value="Visa"
                    onChange={(e) => setSelectedPayment(e.target.value)}
                  />
                  <span className="checkmark"></span>
                </label>
                <label className="container">
                  MasterCard
                  <input
                    type="radio"
                    name="payment"
                    value="MasterCard"
                    onChange={(e) => setSelectedPayment(e.target.value)}
                  />
                  <span className="checkmark"></span>
                </label>
                <label className="container">
                  Blik
                  <input
                    type="radio"
                    name="payment"
                    value="Blik"
                    onChange={(e) => setSelectedPayment(e.target.value)}
                  />
                  <span className="checkmark"></span>
                </label>
              </div>
              <div class="payment-options-images">
                <img src="/images/mastercard.png" alt="MasterCard" />
                <img src="/images/visa.png" alt="Visa" />
                <img src="/images/blik.png" alt="MasterCard" className="payment-blik" />
              </div>

              {/* Buttons */}
              <div className="checkout-buttons">
                <button className="btn return-btn" onClick={() => navigate('/cart')}>
                  Return to Cart
                </button>
                <button className="btn buy-btn" onClick={handleBuy}>
                  Buy Now
                </button>
              </div>
            </div>
          </section>
        )}
      </div>
      {/* Footer */}
      <footer className="footer">
        <p>© 2025 MovieHub. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default Checkout;