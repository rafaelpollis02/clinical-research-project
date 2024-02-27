import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import zxcvbn from 'zxcvbn';
import './ChangePassword.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { useNavigate, useLocation } from 'react-router-dom';

const ChangePassword = () => {
  const navigate = useNavigate();
  const [password, setPassword] = useState('');
  const [fullname, setFullname] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordStrength, setPasswordStrength] = useState({ score: 0, feedback: '' });
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfir, setShowPasswordConfir] = useState(false);
  const location = useLocation();
  const [passwordMismatch, setPasswordMismatch] = useState(false);

  const enteredUser = location.state?.enteredUser;
  const passwordRef = useRef(null);
  const confirmPasswordRef = useRef(null);


  useEffect(() => {
    // Use a requisição para obter o nome do usuário
    const fetchFullname = async () => {
      try {
        const personResponse = await axios.get(`http://localhost:8080/api/v1/person/${encodeURIComponent(enteredUser)}/cpf`);
        const retrievedFullname = personResponse.data.fullName; // Ajuste aqui
  
        // Ajuste para trazer apenas o campo 'fullName'
        setFullname(retrievedFullname);
      } catch (error) {
        console.error('Erro ao obter o nome do usuário:', error);
      }
    };
  
    fetchFullname();
  }, [enteredUser]);

  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
    setPassword(newPassword);

    if (newPassword.trim() === '') {
      setPasswordStrength({ score: 0, feedback: '' });
    } else {
      const strength = zxcvbn(newPassword);
      setPasswordStrength({ score: strength.score, feedback: strength.feedback.suggestions.join(' ') });
    }
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
    
  };

  const handleTogglePassword = () => {
    setShowPassword(!showPassword);
    setShowPasswordConfir(!showPasswordConfir);
  };

  const getScoreLabel = () => {
    if (passwordStrength.score < 3) {
      return 'Fraca';
    } else if (passwordStrength.score < 4) {
      return 'Média';
    } else {
      return 'Forte';
    }
  };

  const getColor = () => {
    if (passwordStrength.score < 3) {
      return '#ff4848'; // Vermelho para senha fraca
    } else if (passwordStrength.score < 4) {
      return '#f9c542'; // Amarelo para senha média
    } else {
      return '#4caf50'; // Verde para senha forte
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const passwordValue = passwordRef.current.value;
    const confirmPasswordValue = confirmPasswordRef.current.value;

    // Verifica se password e confirmPassword são iguais e atendem aos critérios
    if (passwordValue !== confirmPasswordValue || passwordValue.length < 8 || passwordStrength.score < 3) {
      setPasswordMismatch(true);
      return;
    }
  

    const changeResponse = await axios.put(
      `http://localhost:8080/api/v1/autenticate/${enteredUser}/updatePassword`, 
      JSON.stringify({ password: password }),
      { headers: { 'Content-Type': 'application/json' } }
    );

    if (changeResponse.status === 200) {
      console.log('Senha Alterada com sucesso', changeResponse.data);
      navigate("/", { state: { enteredUser } });
    } else {
      console.log(`Resposta inesperada: ${changeResponse.status}`);
    }
  };

  return (
    <div className="create-password-container">
      <form onSubmit={handleSubmit}>
        <div className="user-email">
          <h2>Olá, <span>{fullname}</span></h2>
          <br />
          <br />
        </div>
        <div className="password-input input-with-icon">
          <label>Senha:</label>
          <div className="input-container">
            <input
              type={showPassword ? 'text' : 'password'}
              value={password}
              onChange={handlePasswordChange}
              ref={passwordRef} 
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
              ref={confirmPasswordRef} 
            />
          </div>
        </div>

        <button type="submit" disabled={passwordMismatch || password.length < 8 || passwordStrength.score < 3}>
          Criar Senha
        </button>
        <br />
        {password && password.trim() !== '' && (
          <div className="password-strength" style={{ backgroundColor: getColor() }}>
            <p>{getScoreLabel()}</p>
          </div>
        )}
        
        {passwordMismatch && (
          <div className="password-mismatch-error">
            <p>As senhas não coincidem. Por favor, verifique.</p>
          </div>
        )}
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