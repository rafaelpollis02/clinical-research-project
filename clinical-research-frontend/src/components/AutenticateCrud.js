import React, { useState } from 'react';
import LoggedInScreen from '../Autenticate/LoggedInScreen';
import './AutenticateCrud.css';
import eyeOpen from './olho aberto.png';
import eyeClosed from './olho fechado.png';
import { Link } from 'react-router-dom';
import './AutenticateCrud.css';


const AutenticateForm = () => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  

  const handleLogin = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/autenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ user, password }),
      });

      if (response.ok) {
        setIsLoggedIn(true);
      } else {
        console.error('Authentication failed');
        setIsLoggedIn(false);
        setErrorMessage('Login ou senha inválido');
      }
    } catch (error) {
      console.error('Error during login:', error);
      setIsLoggedIn(false);
    }
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      handleLogin();
    }
  };

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
  };

  if (isLoggedIn) {
    return <LoggedInScreen />;
  }

  return (
    <div className="login">
      <div className="login-container">
        <h1>Faça seu login</h1>
        <label>
        
          <input
            type="text"
            value={user}
            onChange={(e) => setUser(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="E-mail ou CPF"
          />
        </label>

        <label>
          
          <input
            type={showPassword ? 'text' : 'password'}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="Senha"
          />
          <img  className="eye-icon"
            src={showPassword ? eyeOpen : eyeClosed}
            alt={showPassword ? 'Olho aberto' : 'Olho fechado'}
            onClick={handleTogglePassword}
            
          />
        </label>

        <Link to="/password-recovery">Esqueci minha senha</Link>
        <br />

        <button onClick={handleLogin}>Entrar</button>
        {errorMessage && <div style={{ color: 'red' }}>{errorMessage}</div>}
        <br />
        <br />
      </div>
      <div className="image-container">
       
        <img src="Logo.png" alt="" />
      </div>
    </div>
  );
};

export default AutenticateForm;
