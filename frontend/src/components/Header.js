import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { getCartItems, getWatchList, getMyList } from '../api/api'; // Import API methods
import './Header.css';

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation(); // Get the current location

  const [cartCount, setCartCount] = useState(0);
  const [watchlistCount, setWatchlistCount] = useState(0);
  const [myListCount, setMyListCount] = useState(0);

  const accountId = 1; // Replace with the actual account ID from authentication

  useEffect(() => {
    // Fetch counts for cart, watchlist, and my list
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

    fetchCounts();
  }, [accountId]);

  const buttons = [
    { name: `Dashboard`, path: '/dashboard' },
    { name: `Watchlist${watchlistCount > 0 ? ` (${watchlistCount})` : ''}`, path: '/watchlist' },
    { name: `Cart${cartCount > 0 ? ` (${cartCount})` : ''}`, path: '/cart' },
    { name: `My List${myListCount > 0 ? ` (${myListCount})` : ''}`, path: '/mylist' },
  ];

  return (
    <nav>
      <button className="logo" onClick={() => navigate('/')}>
        <span className="movie">Movie</span>
        <span className="hub">Hub</span>
      </button>
      <div>
        {buttons
          .filter(button => location.pathname !== button.path) // Exclude the current page's button
          .map(button => (
            <button key={button.name} onClick={() => navigate(button.path)}>
              {button.name}
            </button>
          ))}
      </div>
    </nav>
  );
};

export default Header;