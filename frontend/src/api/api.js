import axios from 'axios';
import { BASE_URL } from './config';

// Set the base URL for all Axios requests
axios.defaults.baseURL = BASE_URL;

// Add an Axios interceptor to include the token in the Authorization header
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Fetch all cart items for a specific account
export const getCartItems = (accountId) => {
  return axios.get(`/cart/account/${accountId}`);
};

// Remove a movie from the cart
export const removeFromCart = (accountId, movieId) => {
  return axios.delete(`/cart/account/${accountId}/movie/${movieId}`);
};

// Fetch all movies
export const getMovies = () => {
  return axios.get(`/movie/all`);
};

// Fetch all movies by genre
export const getMoviesByGenre = (genreId) => {
  return axios.get(`/movie/genre/${genreId}`);
};

// Add a movie to the cart
export const addToCart = (accountId, movieId) => {
  return axios.post(`/cart/account/${accountId}/movie/${movieId}`);
};

// Add a movie to the watchlist
export const addToWatchList = (accountId, movieId) => {
  return axios.post(`/watchlist/account/${accountId}/movie/${movieId}`);
};

// Fetch the watchlist for a specific account
export const getWatchList = (accountId) => {
  return axios.get(`/watchlist/account/${accountId}/all`);
};

// Remove a movie from the watchlist
export const removeFromWatchList = (accountId, movieId) => {
  return axios.delete(`/watchlist/account/${accountId}/movie/${movieId}`);
};

// Fetch the user's movie list
export const getMyList = (accountId) => {
  return axios.get(`/mylist/account/${accountId}/all`);
};

// Create a new account
export const registerAccount = async (credentials) => {
  try {
    const res = await axios.post(`/account/register`, credentials);
    return res;
  } catch (err) {
    // Log the error for debugging
    console.error('Error registering account:', err.response?.data || err.message);
    throw err; // Re-throw the error to be handled in the calling function
  }
};

// Authenticate account
export const authenticateAccount = async (email) => {
  const res = await axios.get(`/account/${email}`);
  return res.data; 
};

// Order a movie
export const orderMovie = (accountId, movieId) => {
  return axios.post(`/mylist/account/${accountId}/movie/${movieId}`);
};

// Clear the cart
export const clearCart = (accountId) => {
  return axios.delete(`/cart/account/${accountId}/clear`);
};