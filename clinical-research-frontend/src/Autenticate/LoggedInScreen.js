// LoggedInScreen.js
import React, { useState } from 'react';
import PopupMessage from '../Autenticate/PopupMessage';

const LoggedInScreen = () => {
  const [showPopup, setShowPopup] = useState(true);

  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    <div>
      {showPopup && <PopupMessage message="" onClose={closePopup} />}
    
    </div>
  );
};

export default LoggedInScreen;
