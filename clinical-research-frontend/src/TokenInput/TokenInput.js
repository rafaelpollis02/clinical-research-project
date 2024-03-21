import React, { useState, useEffect, useCallback } from 'react';
import './Tokeninput.css';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';

const TokenInput = () => {
  const [fullname, setFullname] = useState('');
  const [token, setToken] = useState(Array(6).fill(''));
  const [isTokenComplete, setIsTokenComplete] = useState(false);
  const [apiMessage, setApiMessage] = useState(null);
  const [countdown, setCountdown] = useState(120); // Tempo em segundos
  const navigate = useNavigate();
  const location = useLocation();
  const enteredUser = location.state?.enteredUser;

  const fetchFullname = useCallback(async () => {
    try {
      const personResponse = await axios.get(`http://localhost:8080/api/v1/person/${encodeURIComponent(enteredUser)}/cpfOrEmail`);
      const retrievedFullname = personResponse.data.fullName;
      setFullname(retrievedFullname);
    } catch (error) {
      console.error('Erro ao obter o nome do usuário:', error);
    }
  }, [enteredUser]);

  const handleConfirm = useCallback(async () => {
    if (!isTokenComplete) {
      // Se o token não estiver completo, não realizar a validação
      console.log('Token incompleto. Aguarde a inserção completa do token.');
      return;
    }
    try {
      const tokenValue = token.join('');
      const response = await axios.get(
        `http://localhost:8080/api/v1/autenticate/${tokenValue}/validateToken`
      );

      if (response.status === 200) {
        console.log('Token validado com sucesso!');
        setApiMessage('Token validado com sucesso!');
        navigate("/change-password", { state: { enteredUser } });
      } else if (response.status === 400) {
        console.log('Token expirado');
        setApiMessage('Token expirado');
      } else if (response.status === 204) {
        console.log('Token não encontrado');
        setApiMessage('Token não encontrado');
      }
    } catch (error) {
      console.error('Erro ao fazer a requisição:', error);
      setApiMessage('Token não encontrado.');
    }
  }, [token, navigate, isTokenComplete, enteredUser]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCountdown((prevCountdown) => {
        console.log('Contagem: ', prevCountdown);
        if (prevCountdown === 0) {
          clearInterval(intervalId);
          console.log('Temporizador atingiu 0, chamando handleConfirm...');
          handleConfirm();
          return 0;
        }
        return prevCountdown - 1;
      });
    }, 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, [countdown, handleConfirm]);

  useEffect(() => {
    fetchFullname();
  }, [fetchFullname]);

  const handleTokenChange = (index, value) => {
    const newToken = [...token];
    newToken[index] = value.replace(/\D/g, '').charAt(0);
    setToken(newToken);

    if (index < 5 && value !== '' && /\d/.test(value)) {
      document.getElementById(`token-input-${index + 1}`).focus();
    }

    setIsTokenComplete(newToken.every((digit) => digit !== ''));

    // Reiniciar o contador a cada mudança no token
    // Resetar o contador para 120 segundos (2 minutos)
  };

  const handleKeyPress = (index, event) => {
    if (index === 5 && event.key === 'Enter' && isTokenComplete) {
      handleConfirm();
    }
  };

  return (
    <div className='token-page'>
    <div className="token-input-container">
      <h2><span>{fullname}</span></h2>
      <h3>Digite o código enviado para seu E-mail:</h3>
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
      < br/>
      < br/>
      <button className="confirm-button" onClick={handleConfirm} disabled={!isTokenComplete}>
        Confirmar
      </button>

      {countdown > 0 && (
        <p style={{ color: 'blue' }}>Tempo restante: {countdown} segundos</p>
      )}

      {apiMessage && <p style={{ color: 'red' }}>{apiMessage}</p>}
      <div className='button-link'> <a href="/password-recovery">Voltar</a></div>
    </div>
    <div class="progress-bar">
       <p>Etapa 2 de 3</p>
        <div class="progress" style={{ width: '66.66%' }}> </div>
        <div className="top-bar">
          <h2>Healthuture</h2></div>
    </div>
    </div>
  );
};

export default TokenInput;
