import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <a className="navbar-brand" href="/">MovieHub</a>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <button className="btn btn-link nav-link" onClick={() => navigate('/dashboard')}>Dashboard</button>
            </li>
            <li className="nav-item">
              <button className="btn btn-link nav-link" onClick={() => navigate('/watchlist')}>Watchlist</button>
            </li>
            <li className="nav-item">
              <button className="btn btn-link nav-link" onClick={() => navigate('/cart')}>Cart</button>
            </li>
            <li className="nav-item">
              <button className="btn btn-link nav-link" onClick={() => navigate('/mylist')}>My List</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Header;