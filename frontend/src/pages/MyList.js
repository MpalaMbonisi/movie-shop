import React, { useEffect, useState } from 'react';
import { getMyList } from '../api/api';
import { useAuth } from '../context/AuthContext';
import Header from '../components/Header';

const MyList = () => {
  const { account } = useAuth();
  const [myList, setMyList] = useState([]);

  useEffect(() => {
    getMyList(account.id).then(res => setMyList(res.data));
  }, [account.id]);

  return (
    <div>
      <Header />
      <div className="container mt-4">
      <h2>My Movies</h2>
      {myList.map(item => (
        <div key={item.id} className="mb-2">
          {item.movie.title}
        </div>
      ))}
    </div>
    </div>
  );
};

export default MyList;