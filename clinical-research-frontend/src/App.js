import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AutenticateForm from './components/AutenticateCrud';
import PasswordRecovery from './PasswordRecovery/PasswordRecovery';
import HomeScreen from './Home/HomeScreen';
import Register from './Register/Register';
import TokenInput from './TokenInput/TokenInput'; 
import ChangePassword from './ChangePassword/ChangePassword'; 
import PopupMessage from './Autenticate/PopupMessage'; 

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AutenticateForm />} />
        <Route path="/popup-message" element={<PopupMessage />} />
        <Route path="/password-recovery" element={<PasswordRecovery />} />
        <Route path="/token-input" element={<TokenInput />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route path="/home" element={<HomeScreen />} />
        <Route path="/register" element={<Register />} /> 
      </Routes>
    </Router>
  );
}

export default App;
