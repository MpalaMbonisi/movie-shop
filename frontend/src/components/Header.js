import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { getCartItems, getWatchList, getMyList } from '../api/api';
import { FaSignOutAlt, FaHome, FaHeart, FaShoppingCart, FaList } from 'react-icons/fa'; // Import icons
import './Header.css';

const Header = ({ accountId }) => {
  const navigate = useNavigate();
  const location = useLocation();

  const [cartCount, setCartCount] = useState(0);
  const [watchlistCount, setWatchlistCount] = useState(0);
  const [myListCount, setMyListCount] = useState(0);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  useEffect(() => {
    setCartCount(0);
    setWatchlistCount(0);
    setMyListCount(0);

    const fetchCounts = async () => {
      try {
        const cartRes = await getCartItems(accountId);
        setCartCount(cartRes.data.length);

        const watchlistRes = await getWatchList(accountId);
        setWatchlistCount(watchlistRes.data.length);

        const myListRes = await getMyList(accountId);
        setMyListCount(myListRes.data.length);
      } catch (error) {
        console.error('Error fetching counts:', error);
      }
    };

    if (accountId) {
      fetchCounts();
    }
  }, [accountId]);

  const buttons = [
    { icon: <FaHome />, path: '/dashboard', count: null, label: ' Dashboard' },
    { icon: <FaHeart />, path: '/watchlist', count: watchlistCount, label: ' Watchlist' },
    { icon: <FaShoppingCart />, path: '/cart', count: cartCount, label: ' Cart' },
    { icon: <FaList />, path: '/mylist', count: myListCount, label: ' My List' },
  ];

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  const onLogout = () => {
    // Clear any user-related data (e.g., tokens, session storage, etc.)
    localStorage.removeItem('authToken'); // Example: Remove token from local storage
    navigate('/'); // Redirect to the landing page
  };

  return (
    <nav>
      <button className="logo" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </button>
      <div className="desktop-menu">
        {buttons
          .filter(button => location.pathname !== button.path)
          .map(button => (
            <button key={button.path} onClick={() => navigate(button.path)} className="icon-button">
              {button.icon}
              {button.count !== null && <span className="count-badge">{button.count}</span>}
            </button>
          ))}
        <button onClick={onLogout} className="icon-button logout-button">
          <FaSignOutAlt />
        </button>
      </div>
      <div className="mobile-menu-icon" onClick={toggleMobileMenu}>
        ☰
      </div>
      <div className={`mobile-menu ${isMobileMenuOpen ? 'open' : ''}`}>
        <img className="nav-image-cinema" src="/images/cinema.png" alt="Cinema" />
        {buttons
          .filter(button => location.pathname !== button.path)
          .map(button => (
            <button key={button.path} onClick={() => navigate(button.path)} className="icon-button">
              <div className="icon-with-label">
                {button.icon}
                <span className="button-label">{button.label}</span>
                {button.count !== null && <span className="count-badge">{button.count}</span>}
              </div>
            </button>
          ))}
        <button onClick={onLogout} className="icon-button">
          <div className="icon-with-label">
            <FaSignOutAlt />
            <span className="button-label"> Logout</span>
          </div>
        </button>
        <button className="close-menu" onClick={toggleMobileMenu}>✖</button>
      </div>
    </nav>
  );
};

export default Header;