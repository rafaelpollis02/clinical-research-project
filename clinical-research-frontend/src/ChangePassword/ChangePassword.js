import React, { useState } from 'react';
import zxcvbn from 'zxcvbn';
import './ChangePassword.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';



const ChangePassword = () => {
  
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordStrength, setPasswordStrength] = useState({ score: 0, feedback: '' });
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfir, setShowPasswordConfir] = useState(false);

  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);
    // Avalie a força da senha usando a biblioteca zxcvbn
    const strength = zxcvbn(newPassword);
    setPasswordStrength({ score: strength.score, feedback: strength.feedback.suggestions.join(' ') });
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
    setShowPasswordConfir(!showPasswordConfir);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Adicione a lógica para enviar a senha para a API ou realizar outras ações necessárias

    if (password !== confirmPassword || password.length < 8 || passwordStrength.score < 3) {
      // Exibir alerta se os critérios não forem atendidos
      alert('Por favor, verifique os critérios de senha.');
      return;
    }

    // Lógica adicional para o caso de a senha atender aos critérios
    console.log('Senha criada:', password);
  };

  return (
    <div className="create-password-container">
      <h2>Criar Nova Senha</h2>
      <form onSubmit={handleSubmit}>
        <div className="password-input input-with-icon">
          <label>Senha:</label>
          <div className="input-container">
            <input
              type={showPassword ? 'text' : 'password'}
              value={password}
              onChange={handlePasswordChange}
            />
         <span className="eye-iconchange" onClick={handleTogglePassword}>
            {showPassword ? (
              <FontAwesomeIcon icon={faEye} />
            ) : (
              <FontAwesomeIcon icon={faEyeSlash} />
            )}
          </span>
          </div>
        </div>
        <div className="confirm-password-input input-with-icon">
          <label>Confirmar Senha:</label>
          <div className="input-container">
            <input
              type={showPasswordConfir ? 'text' : 'password'}
              value={confirmPassword}
              onChange={handleConfirmPasswordChange}
            />
          </div>
        </div>
        <button type="submit" disabled={password !== confirmPassword || password.length < 8 || passwordStrength.score < 3}>
          Criar Senha
        </button>
      </form>
      <div className="password-requirements">
        <p>Requisitos de Senha:</p>
        <ul>
          <li>Mínimo de 8 caracteres</li>
          <li>Pelo menos 1 caractere especial</li>
          <li>Pelo menos 1 número</li>
        </ul>
      </div>
    </div>
  );
};

export default ChangePassword;
