import React, { useEffect, useState } from 'react';
import { getCartItems, removeFromCart } from '../api/api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';

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

  return (
    <div>
      <Header />
      <div className="container mt-4">
        <h2>Your Cart</h2>
        {cart.map(item => (
          <div key={item.id} className="mb-2">
            {item.movie.title}
            <button onClick={() => handleRemove(item.movie.id)} className="btn btn-sm btn-danger ms-3">Remove</button>
          </div>
        ))}
        <button className="btn btn-success mt-3" onClick={() => navigate('/checkout')}>Proceed to Checkout</button>
      </div>
    </div>
  );
};

export default Cart;