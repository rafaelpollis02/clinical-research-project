// PopupMessage.js
import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../Autenticate/PopupMessage.css';

const PopupMessage = ({ message, onClose }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const userName = location.state?.user;

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (typeof onClose === 'function') {
        onClose();
      }
      navigate('/home');
    }, 8000);
  
    // Limpar o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, [onClose, navigate]);

  return (
    <div className="popup-message">
      <div className="popup-content">
        <span className="close-button" onClick={onClose}>
          &times;
        </span>
        <h1>Seja bem-vindo, {userName}!</h1>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default PopupMessage;
