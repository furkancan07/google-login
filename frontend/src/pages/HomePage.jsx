import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';
import axios from 'axios';
import { logout } from '../server/api';

export default function HomePage() {
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [isLogin, setIsLogin] = useState(localStorage.getItem('isLogin') === 'true');
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      axios.get('/api/v1/me', { withCredentials: true })
        .then(() => {
          localStorage.setItem('isLogin', 'true');
          setIsLogin(true);
        })
        .catch(() => {
          localStorage.removeItem('isLogin');
          setIsLogin(false);
        });
    }
  }, [isLogin]);

  const handleLogout = async () => {
    try {
      await logout();
      localStorage.removeItem('isLogin');
      setIsLogin(false);
      navigate('/login');
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div>
      <button className="drawer-hamburger" onClick={() => setDrawerOpen(!drawerOpen)}>
        <span className="hamburger-bar"></span>
        <span className="hamburger-bar"></span>
        <span className="hamburger-bar"></span>
      </button>
      <div className={`drawer-backdrop${drawerOpen ? ' open' : ''}`} onClick={() => setDrawerOpen(false)}></div>
      <nav className={`drawer${drawerOpen ? ' open' : ''}`}>
        <div className="drawer-content">
          <h3>Menü</h3>
          {isLogin ? (
            <button className="drawer-btn" onClick={handleLogout}>Çıkış Yap</button>
          ) : (
            <>
              <button className="drawer-btn" onClick={() => navigate('/login')}>Giriş Yap</button>
              <button className="drawer-btn" onClick={() => navigate('/register')}>Kayıt Ol</button>
            </>
          )}
        </div>
      </nav>
      <div style={{ padding: '2rem', textAlign: 'center' }}>Anasayfa</div>
    </div>
  );
}
