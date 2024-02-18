import React from 'react';
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import AutenticateForm from './components/AutenticateCrud';
import PasswordRecovery from './PasswordRecovery/PasswordRecovery';
import HomeScreen from './Home/HomeScreen'; 
import Register from './Register/Register';

function App() {
  return (
    <Router>
      
        <Routes>
          <Route path="/" element={<AutenticateForm />} />
          <Route path="/password-recovery" element={<PasswordRecovery />} />
          <Route path="/home" element={<HomeScreen />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      
    </Router>
  );
}

export default App;
