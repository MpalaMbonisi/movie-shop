// filepath: c:\Users\Mbonisi\Documents\Java\movie-shop\frontend\src\App.js
import React from 'react';
import { AuthProvider } from './context/AuthContext'; // Import AuthProvider
import { Routes, Route } from 'react-router-dom';
import Landing from './pages/Landing';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Dashboard from './pages/Dashboard';
import Cart from './pages/Cart';
import Checkout from './pages/Checkout';
import MyList from './pages/MyList';
import WatchList from './pages/WatchList';
import ProtectedRoute from './components/ProtectedRoute';
import MovieDetails from './pages/MovieDetails';

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          }
        />
        <Route
          path="/checkout"
          element={
            <ProtectedRoute>
              <Checkout />
            </ProtectedRoute>
          }
        />
        <Route
          path="/mylist"
          element={
            <ProtectedRoute>
              <MyList />
            </ProtectedRoute>
          }
        />
        <Route
          path="/watchlist"
          element={
            <ProtectedRoute>
              <WatchList />
            </ProtectedRoute>
          }
        />
        <Route
          path="/movie/:movieId"
          element={
            <MovieDetails />
          }
        />
      </Routes>
    </AuthProvider>
  );
}

export default App;