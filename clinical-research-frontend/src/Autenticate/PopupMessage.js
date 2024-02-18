// PopupMessage.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Autenticate/PopupMessage.css';

const PopupMessage = ({ message, onClose }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      onClose();
      navigate('/home');
    }, 2000); // 5000 milissegundos = 5 segundos

    // Limpe o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, [onClose, navigate]);

  return (
    <div className="popup-message">
      <div className="popup-content">
        <span className="close-button" onClick={onClose}>
          &times;
        </span>
        <h1>Sucesso!</h1>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default PopupMessage;
