// PopupMessage.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import '../Autenticate/PopupMessage.css';

const PopupMessage = ({ message, onClose, user }) => {
  const navigate = useNavigate();
//  const location = useLocation();
  const fullName = user?.fullName;

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (typeof onClose === 'function') {
        onClose();
      }
      navigate('/home');
    }, 8000);

    // Limpar o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, [onClose, navigate, fullName]);

  return (
    <div className="popup-message">
      <div className="popup-content">
        <span className="close-button" onClick={onClose}>
          &times;
        </span>
        <h1>Seja bem-vindo, {fullName}!</h1>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default PopupMessage;
