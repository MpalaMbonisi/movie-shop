// filepath: c:\Users\Mbonisi\Documents\Java\movie-shop\frontend\src\pages\Checkout.js
import React, { useEffect, useState } from 'react';
import { getCartItems, orderMovie, clearCart } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';

const Checkout = () => {
  const { account } = useAuth();
  const [items, setItems] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getCartItems(account.id).then(res => setItems(res.data));
  }, [account.id]);

  const handleCheckout = async () => {
    try {
      // Simulate ordering all movies in the cart
      for (const item of items) {
        await orderMovie(account.id, item.movie.id);
      }

      // Clear the cart after successful checkout
      await clearCart(account.id);

      alert('Checkout successful! Your cart has been cleared.');
      navigate('/dashboard'); // Redirect to the dashboard
    } catch (err) {
      alert('Failed to complete checkout.');
    }
  };

  return (
    <div>
      <Header />
      <div className="container mt-5">
        <h2>Checkout</h2>
        <ul>
          {items.map(item => (
            <li key={item.id}>{item.movie.title}</li>
          ))}
        </ul>
        <button className="btn btn-success mt-3" onClick={handleCheckout}>
          Complete Checkout
        </button>
      </div>
    </div>
  );
};

export default Checkout;