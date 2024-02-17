// PopupMessage.js
import React, { useEffect } from 'react';
import './PopupMessage.css';

const PopupMessage = ({ message, onClose }) => {
  useEffect(() => {
    const timeoutId = setTimeout(() => {
      onClose();
    }, 5000); // 5000 milissegundos = 5 segundos

    // Limpe o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, [onClose]);

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
