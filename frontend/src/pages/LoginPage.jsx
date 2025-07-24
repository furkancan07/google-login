import React, { useState, useEffect } from 'react';
import './LoginPage.css';
import { login } from '../server/api';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // Google ile giriş sonrası redirect olunduysa, cookie setlenmiş olur
    // isLogin localStorage'a kaydedilsin
    // Basit kontrol: Eğer login-token cookie varsa veya URL'de code/state varsa
    const params = new URLSearchParams(window.location.search);
    if (params.get('code') || params.get('state')) {
      localStorage.setItem('isLogin', 'true');
      // İstersen URL'den parametreleri temizle
      window.history.replaceState({}, document.title, '/login');
    }
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!email || !password) {
      setError('Lütfen tüm alanları doldurun.');
      return;
    }
    setError('');
    setLoading(true);
    try {
      await login({ email, password });
      localStorage.setItem('isLogin', 'true');
      navigate('/');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Giriş Yap</h2>
        <button
          type="button"
          className="google-login-btn"
          onClick={() => {
            localStorage.setItem('isLogin', 'true');
            window.location.href = 'http://localhost:8080/oauth2/authorization/google';
          }}
        >
          <svg width="20" height="20" viewBox="0 0 48 48" style={{ marginRight: 8 }}>
            <g>
              <path fill="#4285F4" d="M24 9.5c3.54 0 6.7 1.22 9.19 3.23l6.85-6.85C35.64 2.13 30.13 0 24 0 14.82 0 6.73 5.8 2.69 14.19l7.98 6.2C12.36 13.36 17.74 9.5 24 9.5z"/>
              <path fill="#34A853" d="M46.1 24.55c0-1.64-.15-3.22-.42-4.74H24v9.01h12.42c-.54 2.9-2.18 5.36-4.65 7.01l7.19 5.6C43.98 37.13 46.1 31.3 46.1 24.55z"/>
              <path fill="#FBBC05" d="M10.67 28.39c-1.13-3.36-1.13-6.97 0-10.33l-7.98-6.2C.7 16.36 0 20.07 0 24c0 3.93.7 7.64 2.69 12.14l7.98-6.2z"/>
              <path fill="#EA4335" d="M24 48c6.13 0 11.64-2.03 15.84-5.53l-7.19-5.6c-2.01 1.35-4.6 2.13-8.65 2.13-6.26 0-11.64-3.86-13.33-9.36l-7.98 6.2C6.73 42.2 14.82 48 24 48z"/>
              <path fill="none" d="M0 0h48v48H0z"/>
            </g>
          </svg>
          Google ile Giriş Yap
        </button>
        <label htmlFor="email">E-posta</label>
        <input
          type="email"
          id="email"
          placeholder="E-posta adresinizi girin"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <label htmlFor="password">Şifre</label>
        <input
          type="password"
          id="password"
          placeholder="Şifrenizi girin"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        {error && <div className="error-message">{error}</div>}
        <button type="submit" disabled={loading}>{loading ? 'Giriş Yapılıyor...' : 'Giriş Yap'}</button>
      </form>
      <div className="signup-info">Kayıt olmadınız mı? <span className="signup-link" onClick={() => navigate('/register')}>Kayıt olun</span></div>
    </div>
  );
} 