import { createContext, useContext, useState, useEffect, useCallback } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      fetchUser();
    } else {
      setLoading(false);
    }
  }, [token]);

  const fetchUser = async (authToken) => {
    const activeToken = authToken || token || localStorage.getItem('token');
    if (!activeToken) {
      setUser(null);
      setLoading(false);
      return;
    }
    try {
      const res = await fetch('/api/auth/me', {
        headers: { 'Authorization': `Bearer ${activeToken}` }
      });
      if (res.ok) {
        const userData = await res.json();
        setUser(userData);
      } else {
        logout();
      }
    } catch (e) {
      logout();
    } finally {
      setLoading(false);
    }
  };

  const login = useCallback(async (username, password) => {
    const res = await fetch('/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });
    if (!res.ok) {
      let msg = 'Login failed';
      try {
        const err = await res.json();
        if (err && err.message) msg = err.message;
      } catch {
        const text = await res.text().catch(() => null);
        if (text) msg = text;
      }
      throw new Error(msg);
    }
    const data = await res.json();
    localStorage.setItem('token', data.token);
    setToken(data.token);
    await fetchUser(data.token);
  }, []);

  const register = useCallback(async (username, email, password) => {
    const res = await fetch('/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, email, password })
    });
    if (!res.ok) {
      let msg = 'Registration failed';
      try {
        const err = await res.json();
        if (err && err.message) msg = err.message;
      } catch {
        const text = await res.text().catch(() => null);
        if (text) msg = text;
      }
      throw new Error(msg);
    }
    const data = await res.json();
    localStorage.setItem('token', data.token);
    setToken(data.token);
    await fetchUser(data.token);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
  }, []);

  return (
    <AuthContext.Provider value={{ token, user, login, register, logout, loading, isAuthenticated: !!token }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}