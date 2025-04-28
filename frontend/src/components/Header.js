import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { getCartItems, getWatchList, getMyList } from '../api/api';
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
    { name: `Dashboard`, path: '/dashboard' },
    { name: `Watchlist${watchlistCount > 0 ? ` (${watchlistCount})` : ''}`, path: '/watchlist' },
    { name: `Cart${cartCount > 0 ? ` (${cartCount})` : ''}`, path: '/cart' },
    { name: `My List${myListCount > 0 ? ` (${myListCount})` : ''}`, path: '/mylist' },
  ];

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
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
            <button key={button.name} onClick={() => navigate(button.path)}>
              {button.name}
            </button>
          ))}
      </div>
      <div className="mobile-menu-icon" onClick={toggleMobileMenu}>
        ☰
      </div>
      <div className={`mobile-menu ${isMobileMenuOpen ? 'open' : ''}`}>
        <img class="nav-image-cinema" src="/images/cinema.png"/>
        {buttons
          .filter(button => location.pathname !== button.path)
          .map(button => (
            <button key={button.name} onClick={() => navigate(button.path)}>
              {button.name}
            </button>
          ))}
        <button className="close-menu" onClick={toggleMobileMenu}>✖</button>
      </div>
    </nav>
  );
};

export default Header;