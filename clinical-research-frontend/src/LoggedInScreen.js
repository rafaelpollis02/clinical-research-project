// LoggedInScreen.js
import React, { useState } from 'react';
import PopupMessage from './PopupMessage';

const LoggedInScreen = () => {
  const [showPopup, setShowPopup] = useState(true);

  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    <div>
      {showPopup && <PopupMessage message="" onClose={closePopup} />}
      {/* O restante do seu conteúdo permanece o mesmo */}
    </div>
  );
};

export default LoggedInScreen;
