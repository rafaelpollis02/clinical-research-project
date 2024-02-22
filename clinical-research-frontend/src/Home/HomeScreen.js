import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './HomeScreen.css';

// ... importações e código anterior

const HomeScreen = () => {
  const location = useLocation();
  const userName = location.state?.user;
  const navigate = useNavigate();

  const handleLogout = () => {
    // Lógica de logout aqui, por exemplo, limpar o token de autenticação
    // Após o logout, redirecionar para a tela de autenticação
    navigate('/');
  };

  const handleCadastro = () => {
    // Adicione a lógica para navegar para a tela de cadastro
    navigate('/register');
  };

  const handlePesquisaClinica = () => {
    // Adicione a lógica para navegar de volta para a tela de home
    navigate('/home');
  };

  return (
    <div className="home-container">
      <div className="top-bar">
        <h2>Healthuture</h2>
        <p>Bem-vindo, {userName}!</p>
        <button className="logout-button" onClick={handleLogout}>
          Sair
        </button>
      </div>
      <div className="menu-container">
        <div className="menu">
          <div className="menu-item">
            <button className="Cadastro" onClick={handleCadastro}>
              <i className="fas fa-user-plus" style={{ verticalAlign: 'top' }}></i> Cadastro
            </button>
            <br />
            <button className="Agenda" onClick={handlePesquisaClinica}>
              <i className="fas fa-notes-medical" style={{ verticalAlign: 'top' }}></i> Agenda
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomeScreen;
