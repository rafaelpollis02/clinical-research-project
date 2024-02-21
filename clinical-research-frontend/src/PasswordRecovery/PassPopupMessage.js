// PassPopupMessage.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './PassPopupMessage.css';

const PassPopupMessage = ({ message, onClose }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      onClose();
      
    }, 10000); 
    navigate('/TokenInput');

    // Limpe o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, [onClose, navigate]);

  return (
    <div className="popup-message">
      <div className="popup-content">
        <span className="close-button" onClick={onClose}>
          &times;
        </span>
        <h1>Enviamos para seu e-mail um passo a passo para recuperar a senha</h1>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default PassPopupMessage;
