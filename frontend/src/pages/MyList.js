import React, { useEffect, useState } from 'react';
import { getMyList } from '../api/api';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import './mylist.css'; // Import the CSS file for styling

const MyList = () => {
  const accountId = localStorage.getItem('accountId');
  const [myList, setMyList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getMyList(accountId).then(res => setMyList(res.data));
  }, [accountId]);

  return (
    <div>
      <Header accountId={accountId} />
      <div className="mylist-container">
        {myList.length === 0 ? (
          <p className="mylist-empty-text">Your list is empty😢.</p>
        ) : (
          <div className="mylist-items">
            <h2 class="mylist-title" >My movies </h2>
            {myList.map(item => (
              <div key={item.id} className="mylist-item">
                {/* Poster redirects to movie details page */}
                <img
                  src={item.movie.poster}
                  alt={item.movie.title}
                  className="mylist-item-poster" // Add pointer cursor for better UX
                />
                <div className="mylist-item-details">
                  <h4>{item.movie.title}</h4>
                  <div className="item-details">
                    <div>
                      <p><strong>Year:</strong> {item.movie.releaseYear}</p>
                      <p className="mylist-cast"><strong>Cast:</strong> {item.movie.actors}</p>
                    </div>
                    <div className="align-left">
                      <p><strong>Duration:</strong> {item.movie.duration}</p>
                      <p><strong>Genre:</strong> {item.movie.genre.genre}</p>
                    </div>
                  </div>
                  <div className="mylist-buttons-grid">
                    <button
                      onClick={() => window.open(item.movie.trailer, '_blank')}
                      className="btn"
                    >
                      Watch
                    </button>
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

export default MyList;