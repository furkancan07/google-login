import axios from 'axios';

var url="/api/v1/"
export async function login({ email, password }) {
  try {
    const response = await axios.post(url+'auth/login', { email, password });
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || 'Giriş başarısız.';
    throw new Error(message);
  }
}

export async function register({ email, password }) {
  try {
    const response = await axios.post(url+'auth/register', { email, password });
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || 'Kayıt başarısız.';
    throw new Error(message);
  }
}

export async function logout() {
  try {
    const response = await axios.delete(url+'auth/logout', { withCredentials: true });
    return response.data;
  } catch (error) {
    const message = error.response?.data?.message || 'Çıkış başarısız.';
    throw new Error(message);
  }
} 