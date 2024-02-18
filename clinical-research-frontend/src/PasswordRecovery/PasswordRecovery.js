import React, { useState } from 'react';
import './PasswordRecovery.css';

const PasswordRecovery = () => {
  const [email, setEmail] = useState('');

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordRecovery = () => {
    console.log(`Enviando solicitação de recuperação de senha para: ${email}`);
  };

  return (
    <div className="password-recovery-container">
      
      <h3>Informe seu e-mail para redefinir a senha.</h3>
      
      <input type="email" id="email" name="email" value={email} onChange={handleEmailChange} required placeholder="E-mail"/>
      <br />
      <button onClick={handlePasswordRecovery}>Enviar E-mail de Recuperação</button>
      <br />
      <a href="/">Voltar para o Login</a>
    </div>
  );
}

export default PasswordRecovery;
