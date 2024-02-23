import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './PasswordRecovery.css';

const PasswordRecovery = () => {
  const [user, setUser] = useState('');
  const [enteredUser, setEnteredUser] = useState('');
  const [showFailure, setShowFailure] = useState(false);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [showInvalid, setShowInvalid] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    console.log('Value of enteredUser:', enteredUser);
  }, [enteredUser]);

  const handleEmailCpfChange = (e) => {
    setUser(e.target.value);
  };

  const isEmail = (value) => /^\S+@\S+\.\S+$/.test(value);
  const isCPF = (value) => /^\d{11}$/.test(value.replace(/\D/g, ''));

  const handlePasswordRecovery = async (e) => {
    e.preventDefault();

    try {
      if (isEmail(user) || isCPF(user)) {
        const response = await axios.get(`http://localhost:8080/api/v1/autenticate/${encodeURIComponent(user)}`);

        if (response.status === 200) {
          setShowSuccessMessage(true);
          setShowErrorMessage(false);
          setEnteredUser(user);

          const tokenResponse = await axios.post('http://localhost:8080/api/v1/autenticate/generateToken', {
            user: user,
          });

          if (tokenResponse.status === 200) {
            console.log('Token gerado com sucesso:', tokenResponse.data);
            navigate('/change-password', { state: { enteredUser: user } });
          } else {
            console.error('Falha ao gerar o token.');
          }
        } else {
          console.error('Authentication failed');
          setShowSuccessMessage(false);
          setShowErrorMessage(true);
        }
      } else {
        console.error('Formato de e-mail ou CPF inválido.');
        setShowInvalid(true);
      }
    } catch (error) {
      console.error('Erro ao fazer a requisição:', error);
      setShowFailure(true);
    }
  };

  return (
    <div className="password-recovery-container">
      <h3>Informe seu e-mail ou CPF para redefinir a senha.</h3>
      <form onSubmit={handlePasswordRecovery}>
        <input
          type="text"
          id="emailCpf"
          name="emailCpf"
          value={user}
          onChange={handleEmailCpfChange}
          required
          placeholder="E-mail ou CPF"
        />
        <br />
        <button type="submit">Enviar E-mail de Recuperação</button>
      </form>
      {showFailure && <p style={{ color: 'red' }}>Dados não localizados em nossa base!</p>}
      {showInvalid && <p style={{ color: 'red' }}>Formato de e-mail ou CPF inválido.</p>}
      {showSuccessMessage && <p style={{ color: 'green' }}>Enviaremos um e-mail com as novas instruções</p>}
      {showErrorMessage && <p style={{ color: 'red' }}>Dados não localizados em nossa base!</p>}

      <a href="/">Voltar para o Login</a>
    </div>
  );
};

export default PasswordRecovery;
