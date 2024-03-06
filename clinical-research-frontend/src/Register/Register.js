import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
  const navigate = useNavigate();

  return (
    <div className="register-container">
      <div className="top-bar-register">
        <h2>Healthuture</h2>
      </div>
      <div className="Cadastros">
        <div className="botao">
          <button className="voltar" onClick={() => navigate('/home')}>
            <i className="fas fa-arrow-left"></i>
            
          </button>
        </div>
        <div className="botao">
          <button className="usuario" onClick={() => navigate('/register')}>
            <i className="fas fa-user"></i>
            <span>Usuário</span>
          </button>
        </div>
        <div className="botao">
          <button className="estabelecimento" onClick={() => navigate('/register-establishment')}>
            <i className="fas fa-hospital"></i>
            <span>Estabelecimento</span>
          </button>
        </div>
        <div className="botao">
          <button className="empresa" onClick={() => navigate('/register-company')}>
            <i className="fas fa-building"></i>
            <span>Empresa</span>
          </button>
        </div>
        <div className="botao">
          <button className="termos" onClick={() => navigate('/register')}>
            <i className="fas fa-file-alt"></i>
            <span>Termos</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default Register;