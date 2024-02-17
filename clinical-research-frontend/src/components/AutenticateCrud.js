import React, { useState } from 'react';
import LoggedInScreen from '../LoggedInScreen'; // Importe o novo componente
import './AutenticateCrud.css'; // Importe o arquivo CSS

const AutenticateForm = () => {
  const [user, setUser] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

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
      }
    } catch (error) {
      console.error('Error during login:', error);
      setIsLoggedIn(false);
    }
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      // Se a tecla pressionada for "Enter", chame a função handleLogin
      handleLogin();
    }
  };

  if (isLoggedIn) {
    // Se estiver autenticado, renderize a tela de sucesso
    return <LoggedInScreen />;
  }

  return (
    <div>
      <h1>Seja Bem vindo</h1>
      <label>
        User:
        <input type="text" value={user} onChange={(e) => setUser(e.target.value)} onKeyPress={handleKeyPress} />
      </label>
      <br />
      <label>
        Password:
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} onKeyPress={handleKeyPress} />
      </label>
      <br />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default AutenticateForm;
