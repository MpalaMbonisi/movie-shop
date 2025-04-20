import axios from 'axios';
import { BASE_URL } from './config';

// Fetch all cart items for a specific account
export const getCartItems = (accountId) => {
  return axios.get(`${BASE_URL}/cart/account/${accountId}`);
};

// Remove a movie from the cart
export const removeFromCart = (accountId, movieId) => {
  return axios.delete(`${BASE_URL}/cart/account/${accountId}/movie/${movieId}`);
};

// Fetch all movies
export const getMovies = () => {
  return axios.get(`${BASE_URL}/movie/all`);
};

// Fetch all movies by genre 
export const getMoviesByGenre = (genreId) => {
  return axios.get(`${BASE_URL}/movie/genre/${genreId}`);
};

// Add a movie to the cart
export const addToCart = (accountId, movieId) => {
  return axios.post(`${BASE_URL}/cart/account/${accountId}/movie/${movieId}`);
};

// Add a movie to the watchlist
export const addToWatchList = (accountId, movieId) => {
  return axios.post(`${BASE_URL}/watchlist/account/${accountId}/movie/${movieId}`);
};

// Fetch the watchlist for a specific account
export const getWatchList = (accountId) => {
  return axios.get(`${BASE_URL}/watchlist/account/${accountId}/all`);
};

// Remove a movie from the watchlist
export const removeFromWatchList = (accountId, movieId) => {
  return axios.delete(`${BASE_URL}/watchlist/account/${accountId}/movie/${movieId}`);
};

// Fetch the user's movie list
export const getMyList = (accountId) => {
  return axios.get(`${BASE_URL}/mylist/account/${accountId}/all`);
};

// Create a new account
export const createAccount = (accountData) => {
  return axios.post(`${BASE_URL}/account`, accountData); // No need for 'no-cors'
};

// Fetch account details
export const getAccount = (accountId) => {
  return axios.get(`${BASE_URL}/account/${accountId}`);
};

// Order a movie
export const orderMovie = (accountId, movieId) => {
  return axios.post(`${BASE_URL}/mylist/account/${accountId}/movie/${movieId}`);
};

// Clear the cart
export const clearCart = (accountId) => {
  return axios.delete(`${BASE_URL}/cart/account/${accountId}/clear`);
};
