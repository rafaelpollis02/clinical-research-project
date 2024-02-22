import React, { useState, useEffect, useRef } from 'react';
import './Tokeninput.css';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const TokenInput = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const userIdentifierRef = useRef('');

  const [userIdentifier, setUserIdentifier] = useState('');
  const [token, setToken] = useState(Array(6).fill(''));
  const [isTokenComplete, setIsTokenComplete] = useState(false);
  const [apiMessage, setApiMessage] = useState(null);
  const [enteredEmail, setEnteredEmail] = useState('');

  // eslint-disable-next-line react-hooks/exhaustive-deps
  useEffect(() => {
    const { email, cpf } = location.state || {};

    if (email) {
      setEnteredEmail(email);
      setUserIdentifier(email);
      userIdentifierRef.current = email;
    } else if (cpf) {
      setUserIdentifier(cpf);
      userIdentifierRef.current = cpf;
    }

    
  }, [location.state]);

  const handleTokenChange = (index, value) => {
    const newToken = [...token];
    newToken[index] = value.replace(/\D/g, '').charAt(0);
    setToken(newToken);

    if (index < 5 && value !== '' && /\d/.test(value)) {
      document.getElementById(`token-input-${index + 1}`).focus();
    }

    setIsTokenComplete(newToken.every((digit) => digit !== ''));
  };

  const handleConfirm = async () => {
    try {
      const tokenValue = token.join('');
      const response = await axios.get(
        `http://localhost:8080/api/v1/autenticate/${tokenValue}/validateToken`
      );

      if (response.status === 200) {
        console.log('Token validado com sucesso!');
        setApiMessage('Token validado com sucesso!');
        navigate("/change-password", { state: { enteredUser: userIdentifier } });
      } else if (response.status === 400) {
        console.log('Token expirado');
        setApiMessage('Token expirado');
      } else if (response.status === 404) {
        console.log('Token não encontrado');
        setApiMessage('Token não encontrado');
      }
    } catch (error) {
      console.error('Erro ao fazer a requisição:', error);
      setApiMessage('Erro ao fazer a requisição.');
    }
  };

  const handleKeyPress = (index, event) => {
    if (index === 5 && event.key === 'Enter' && isTokenComplete) {
      handleConfirm();
    }
  };

  return (
    <div className="token-input-container">
      <h3>Digite o código enviado para {enteredEmail ? 'seu e-mail' : 'seu CPF'}:</h3>
      <div className="token-input-wrapper">
        {token.map((digit, index) => (
          <input
            key={index}
            type="text"
            maxLength="1"
            value={digit}
            onChange={(e) => handleTokenChange(index, e.target.value)}
            onKeyDown={(e) => handleKeyPress(index, e)}
            id={`token-input-${index}`}
            className="token-input"
          />
        ))}
      </div>
      <button className="confirm-button" onClick={handleConfirm} disabled={!isTokenComplete}>
        Confirmar
      </button>

      {apiMessage && <p style={{ color: 'green' }}>{apiMessage}</p>}
      <div className='button-link'> <a href="/password-recovery"><i className="fas fa-arrow-left"></i>Voltar</a></div>
    </div>
  );
};

export default TokenInput;
