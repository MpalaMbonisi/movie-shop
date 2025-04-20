import React, { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [account, setAccount] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedAccount = localStorage.getItem('account');
    if (storedAccount) {
      setAccount(JSON.parse(storedAccount));
    }
  }, []);

  const login = (accountData) => {
    setAccount(accountData);
    localStorage.setItem('account', JSON.stringify(accountData)); // Persist login
  };

  const logout = () => {
    setAccount(null);
    localStorage.removeItem('account');
    navigate('/login');
  };

  const isAuthenticated = !!account;

  return (
    <AuthContext.Provider value={{ account, login, logout, isAuthenticated }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);