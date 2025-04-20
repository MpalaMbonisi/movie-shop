import React, { useEffect, useState } from 'react';
import { getWatchList } from '../api/api';
import { useAuth } from '../context/AuthContext';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';

const WatchList = () => {
  const { account } = useAuth();
  const [watchlist, setWatchlist] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getWatchList(account.id).then(res => setWatchlist(res.data));
  }, [account.id]);

  return (
    <div>
      <Header />
      <div className="container mt-5">
        <h2>Your Watchlist</h2>
        <div className="row">
          {watchlist.map(item => (
            <div key={item.id} className="col-md-3 mb-4">
              <div className="card">
                <img src={item.movie.posterUrl} className="card-img-top" alt={item.movie.title} />
                <div className="card-body">
                  <h5 className="card-title">{item.movie.title}</h5>
                  <p className="card-text">{item.movie.description}</p>
                  <button className="btn btn-primary" onClick={() => navigate(`/movie/${item.movie.id}`)}>View Details</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default WatchList;