// PassInScreen.js
import React, { useState, useEffect } from 'react';
import PassPopupMessage from './PassPopupMessage';

const PassInScreen = () => {
  const [showPopup, setShowPopup] = useState(true);

  const closePopup = () => {
    setShowPopup(false);
  };

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setShowPopup(false);
    },2000); 

    // Limpe o timeout quando o componente for desmontado
    return () => clearTimeout(timeoutId);
  }, []);

  return (
    <div>
      {showPopup && <PassPopupMessage message="" onClose={closePopup} />}
    </div>
  );
};

export default PassInScreen;
