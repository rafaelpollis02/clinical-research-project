import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import AutenticateForm from './Autenticate/AutenticateCrud';
import PasswordRecovery from './PasswordRecovery/PasswordRecovery';
import HomeScreen from './Home/HomeScreen';
import Register from './Register/Register';
import TokenInput from './TokenInput/TokenInput'; 
import ChangePassword from './ChangePassword/ChangePassword'; 
import Company from './RegisterCompany/RegisterCompany'; 
import Establishment from './RegisterEstablishment/RegisterEstablishment';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AutenticateForm />} />
  
        <Route path="/password-recovery" element={<PasswordRecovery />} />
        <Route path="/token-input" element={<TokenInput />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route path="/home" element={<HomeScreen />} />
        <Route path="/register" element={<Register />} /> 
        <Route path="/register-company" element={<Company />} /> 
        <Route path="/register-establishment" element={<Establishment />} /> 
      </Routes>
    </Router>
  );
}

export default App;
