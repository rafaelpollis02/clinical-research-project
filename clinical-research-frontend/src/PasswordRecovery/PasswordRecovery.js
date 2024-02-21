import React, { useState } from 'react';
import './PasswordRecovery.css';

const PasswordRecovery = () => {
  const [User, setUser] = useState('');
  const [errorMessage, setErrorMessage] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [showErrorMessage, setShowErrorMessage] = useState(false);

  const handleEmailCpfChange = (e) => {
    setUser(e.target.value);
  };

  const isEmail = (value) => /^\S+@\S+\.\S+$/.test(value);
  const isCPF = (value) => /^\d{11}$/.test(value.replace(/\D/g, ''));

  const handlePasswordRecovery = async (e) => {
    e.preventDefault(); // Evita o comportamento padrão de recarregar a página ao enviar o formulário

    try {
      if (isEmail(User) || isCPF(User)) {
        const response = await fetch(`http://localhost:8080/api/v1/authenticate?User=${encodeURIComponent(User)}`);

        if (response.status === 200) {
          setShowSuccessMessage(true);
          setShowErrorMessage(false);
        } else {
          console.error('Authentication failed');
          setShowSuccessMessage(false);
          setShowErrorMessage(true);
        }
      } else {
        console.error('Formato de e-mail ou CPF inválido.');
        setErrorMessage('Formato de e-mail ou CPF inválido.');
      }
    } catch (error) {
      console.error('Erro ao fazer a requisição:', error);
      setErrorMessage('Erro ao tentar recuperar senha');
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
          value={User}
          onChange={handleEmailCpfChange}
          required
          placeholder="E-mail ou CPF"
        />
        <br />
        <button type="submit">Enviar E-mail de Recuperação</button>
      </form>

      {errorMessage && <p>{errorMessage}</p>}
      {showSuccessMessage && <p style={{ color: 'green' }}>Enviaremos um e-mail com as novas instruções</p>}
      {showErrorMessage && <p style={{ color: 'red' }}>Dados não localizados em nossa base!</p>}

      <a href="/">Voltar para o Login</a>
    </div>
  );
};

export default PasswordRecovery;
