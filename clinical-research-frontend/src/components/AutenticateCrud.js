import React, { useState } from 'react';
import PopupMessage from '../Autenticate/PopupMessage';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';  // Importe o módulo axios
import './AutenticateCrud.css';

const AutenticateForm = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showSuccessPopup, setShowSuccessPopup] = useState(false);
  const [fullName, setFullName] = useState('');

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
        // Login bem-sucedido, agora obtenha o nome completo
        const fullNameResponse = await axios.get(`http://localhost:8080/api/v1/person/${encodeURIComponent(user)}/cpf`);
        setFullName(fullNameResponse.data.fullName);

        setShowSuccessPopup(true);
        setTimeout(() => {
          setShowSuccessPopup(false);
        }, 3000);

        navigate('/popup-message',  { state: { user, message: 'Mensagem de boas-vindas!' } });
      } else {
        console.error('Authentication failed');
        setShowSuccessPopup(false);
        setErrorMessage('Login ou senha inválido');
      }
    } catch (error) {
      console.error('Error during login:', error);
      setShowSuccessPopup(false);
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
          <span className="eye-iconautentic" onClick={handleTogglePassword}>
            {showPassword ? (
              <FontAwesomeIcon icon={faEye} />
            ) : (
              <FontAwesomeIcon icon={faEyeSlash} />
            )}
          </span>
        </label>

        <br />
        <br />

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

      {showSuccessPopup && <PopupMessage message={`Login bem-sucedido! Bem-vindo, ${fullName}!`} onClose={() => setShowSuccessPopup(false)} />}
    </div>
  );
};

export default AutenticateForm;
