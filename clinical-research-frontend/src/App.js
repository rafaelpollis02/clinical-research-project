import React from 'react';
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import AutenticateForm from './components/AutenticateCrud';
import PasswordRecovery from './PasswordRecovery/PasswordRecovery';

function App() {
  return (
    <Router>
      
        <Routes>
          <Route path="/" element={<AutenticateForm />} />
          <Route path="/password-recovery" element={<PasswordRecovery />} />
        </Routes>
      
    </Router>
  );
}

export default App;
